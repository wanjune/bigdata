Add Jar to **CLASSPATH**
``` HiveQL
add jar /home/wanjune/UDF/udf-1.0-SNAPSHOT.jar;
CREATE TEMPORARY FUNCTION fullwidth2halfwidth AS "org.wanjune.bigdata.udf.UDFFullwidth2Halfwidth";
CREATE TEMPORARY FUNCTION halfwidth2fullwidth AS "org.wanjune.bigdata.udf.UDFHalfwidth2Fullwidth";
``` 
Test for : Full-width to Half-width
``` HiveQL
select fullwidth2halfwidth("１３１２３４５６７８９");
select fullwidth2halfwidth(null);
```
Test for : Half-width to Full-width
``` HiveQL
select halfwidth2fullwidth("13123456789");
select halfwidth2fullwidth(null);
```
