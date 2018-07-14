package org.verdictdb.core.scrambling;

import java.util.HashMap;
import java.util.Map;

import org.verdictdb.core.execplan.MethodInvocationInformation;
import org.verdictdb.core.querying.ExecutableNodeBase;

public class ColumnMetadataRetrievalNode extends ExecutableNodeBase {
  
  /**
   * for which to retrieve metadata
   */
  private String schemaName; 
  
  /**
   * for which to retrieve metadata
   */
  private String tableName;
  
  /**
   * This key should be passed when specifying what methods should be called on DbmsConnection.
   */
  private String tokenKey;
  
  private ColumnMetadataRetrievalNode() { super(); }
  
  static public ColumnMetadataRetrievalNode create(
      String oldSchemaName, String oldTableName, String tokenKey) {
    ColumnMetadataRetrievalNode node = new ColumnMetadataRetrievalNode();
    node.schemaName = oldSchemaName;
    node.tableName = oldTableName;
    node.tokenKey = tokenKey;
    return node;
  }
  
  @Override
  public Map<String, MethodInvocationInformation> getMethodsToInvokeOnConnection() {
    Map<String, MethodInvocationInformation> tokenKeyAndMethods = new HashMap<>();
    MethodInvocationInformation method = 
        new MethodInvocationInformation("getColumns", 
            new Class<?>[] {String.class, String.class},
            new Object[] {schemaName, tableName});
    tokenKeyAndMethods.put(tokenKey, method);
    return tokenKeyAndMethods;
  }

}
