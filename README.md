# presto-hll
Presto HLL Library, using Aggregate Knowledge's awesome java-hll implementation:


The UDF's can be used to work with *HLL strings* which were generated in other systems:

See:
* https://github.com/xadrnd/pig-hll
* https://github.com/aggregateknowledge/hll-storage-spec

Build:
```
mvn clean package
```

Deploy ```target/xad-presto-hll-1.0.jar``` in ```${PRESTO_HOME}/plugin/xad-hll/``` and restart the presto service

Verify installed correctly using ```select hll_udf_version();```



