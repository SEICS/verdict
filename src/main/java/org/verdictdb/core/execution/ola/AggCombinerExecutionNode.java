package org.verdictdb.core.execution.ola;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.verdictdb.connection.DbmsConnection;
import org.verdictdb.core.execution.CreateTableAsSelectExecutionNode;
import org.verdictdb.core.execution.ExecutionInfoToken;
import org.verdictdb.core.execution.ExecutionTokenQueue;
import org.verdictdb.core.execution.QueryExecutionNode;
import org.verdictdb.core.query.AbstractRelation;
import org.verdictdb.core.query.AliasedColumn;
import org.verdictdb.core.query.BaseColumn;
import org.verdictdb.core.query.BaseTable;
import org.verdictdb.core.query.ColumnOp;
import org.verdictdb.core.query.SelectItem;
import org.verdictdb.core.query.SelectQuery;
import org.verdictdb.exception.VerdictDBException;

public class AggCombinerExecutionNode extends CreateTableAsSelectExecutionNode {

  private AggCombinerExecutionNode(String scratchpadSchemaName) {
    super(scratchpadSchemaName);
  }
  
  public static AggCombinerExecutionNode create(
      String scratchpadSchemaName,
      QueryExecutionNode leftQueryExecutionNode,
      QueryExecutionNode rightQueryExecutionNode) {
    AggCombinerExecutionNode node = new AggCombinerExecutionNode(scratchpadSchemaName);
    
//    SelectQuery leftQuery = queryExecutionNode.getSelectQuery();
    SelectQuery rightQuery = rightQueryExecutionNode.getSelectQuery();   // the right one is the aggregate query
    String leftAliasName = node.generateUniqueName();
    String rightAliasName = node.generateUniqueName();
    
    List<String> groupAliasNames = new ArrayList<>();
    List<String> measureAliasNames = new ArrayList<>();
    for (SelectItem item : rightQuery.getSelectList()) {
      if (item.isAggregateColumn()) {
        measureAliasNames.add(((AliasedColumn) item).getAliasName());
      } else {
        groupAliasNames.add(((AliasedColumn) item).getAliasName());
      }
    }
    
    // compose a join query
    List<SelectItem> groupItems = new ArrayList<>();
    List<SelectItem> measureItems = new ArrayList<>();
    for (String a : groupAliasNames) {
      groupItems.add(new AliasedColumn(new BaseColumn(leftAliasName, a), a));
    }
    for (String a : measureAliasNames) {
      measureItems.add(new AliasedColumn(
          ColumnOp.add(new BaseColumn(leftAliasName, a), new BaseColumn(rightAliasName, a)),
          a));
    }
    List<SelectItem> allItems = new ArrayList<>();
    allItems.addAll(groupItems);
    allItems.addAll(measureItems);
    
    Pair<BaseTable, ExecutionTokenQueue> leftBaseAndQueue = node.createPlaceHolderTable(leftAliasName);
    Pair<BaseTable, ExecutionTokenQueue> rightBaseAndQueue = node.createPlaceHolderTable(rightAliasName);
    SelectQuery joinQuery = SelectQuery.create(
        allItems, 
        Arrays.<AbstractRelation>asList(leftBaseAndQueue.getLeft(), rightBaseAndQueue.getLeft()));
    for (String a : groupAliasNames) {
      joinQuery.addFilterByAnd(
          ColumnOp.equal(new BaseColumn(leftAliasName, a), new BaseColumn(rightAliasName, a)));
    }
    leftQueryExecutionNode.addBroadcastingQueue(leftBaseAndQueue.getRight());
    rightQueryExecutionNode.addBroadcastingQueue(rightBaseAndQueue.getRight());
    
    node.setSelectQuery(joinQuery);
    node.addDependency(leftQueryExecutionNode);
    node.addDependency(rightQueryExecutionNode);
    return node;
  }

  @Override
  public ExecutionInfoToken executeNode(DbmsConnection conn, List<ExecutionInfoToken> downstreamResults) 
      throws VerdictDBException {
    ExecutionInfoToken token = super.executeNode(conn, downstreamResults);
    return token;
  }

}
