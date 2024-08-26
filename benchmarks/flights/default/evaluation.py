import pyverdict

def evaluation(dbname, table, query_path, host="localhost", port="5432", user="root", password="", sampling_rate=0.01):
    verdict_conn = pyverdict.postgres(host, port, dbname, user, password) # connect Postgres DB
    # verdict_conn.sql("DROP SCHEMA IF EXISTS verdictdbtemp;") # verdictdb doesn't support this type of query
    # debug: "SELECT COUNT(*) FROM flights_default;"
    # setup_query = f"CREATE SCRAMBLE public.{table}_sampled FROM public.{table} SIZE {sampling_rate};" # create scramble
    # verdict_conn.sql(setup_query)
    
    with open(query_path, "r") as f:
        real_query = f.readlines()
    
    for i, q in enumerate(real_query):
        print(f"i: {i}")
        psql_df = verdict_conn.sql(q.replace("flights", f"{table}"))
        print(f"Postgres time taken: {psql_df}")
        verdict_df = verdict_conn.sql(q.replace("flights", f"{table}_sampled"))
        print(f"VerdictDB time taken: {verdict_df}")
    
if __name__ == "__main__":
    dbname = "flights"
    table = "flights_default"
    query_path = "/Users/jw/Desktop/verdict/benchmarks/flights/aqp_queries.sql"
    evaluation(dbname, table, query_path)