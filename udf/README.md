## INIT
``` HiveQL
-- -----------------------------------------------------------------------
-- Add Jar to $CLASS_PATH$
-- -----------------------------------------------------------------------
add jar /home/wanjune/UDF/udf-1.0-SNAPSHOT.jar;
CREATE TEMPORARY FUNCTION fullwidth2halfwidth AS "org.wanjune.bigdata.udf.UDFFullwidth2Halfwidth";
CREATE TEMPORARY FUNCTION halfwidth2fullwidth AS "org.wanjune.bigdata.udf.UDFHalfwidth2Fullwidth";
CREATE TEMPORARY FUNCTION quarterex AS "org.wanjune.bigdata.udf.UDFQuarter";
CREATE TEMPORARY FUNCTION clear_text AS "org.wanjune.bigdata.udf.UDFClearText";
CREATE TEMPORARY FUNCTION to_date2 AS "org.wanjune.bigdata.udf.UDFToDate";
``` 
## USE
``` HiveQL 
-- -----------------------------------------------------------------------
-- UDFFullwidth2Halfwidth & UDFHalfwidth2Fullwidth
-- -----------------------------------------------------------------------
select fullwidth2halfwidth(null);
-- (NOTHING)
select fullwidth2halfwidth("１３１２３４５６７８９");
-- 13123456789
select fullwidth2halfwidth("１3１２３４５６7８9");
-- 13123456789
select fullwidth2halfwidth("１　3１A３ｂ５６ 7！８　9");
-- 1 31A3b56 7!8 9
 
select halfwidth2fullwidth(null);
-- (NOTHING)
select halfwidth2fullwidth("13123456789");
-- １３１２３４５６７８９
select halfwidth2fullwidth("1３123４56789");
-- １３１２３４５６７８９
select halfwidth2fullwidth("1３1 23４ 5A　6 b7ｃ8　9");
-- １３１　２３４　５Ａ　６　ｂ７ｃ８　９

-- -----------------------------------------------------------------------
-- UDFQuarter
-- Date format : "yyyy-MM-dd HH:mm:ss zzz"
                 "yyyy-MM-dd HH:mm:ss EE"
                 "yyyy-MM-dd HH:mm:ss"
                 "yyyy-MM-dd HH:mm"
                 "yyyy-MM-dd"
                 "yyyyMMddHHmmss"
                 "yyyyMMdd"
                 "yyyy年MM月dd日"
                 "yyyy/MM/dd"
                 Date (Hive)
                 Timestamp (Hive)
-- Quarter format : Stochastic Combination of year[yyyy] and quarter[QR/qr] 
-- -----------------------------------------------------------------------
select quarterex(null);
-- (NOTHING)
select quarterex(null, 'yyyyQR');
-- (NOTHING)
select quarterex("20190101");
-- 2019Q1
select quarterex("2019-03-31");
-- 2019Q1
select quarterex("2019/04/01");
-- 2019Q2
select quarterex("2019年06月30日");
-- 2019Q2
select quarterex("2019-07-01 11:21:30");
-- 2019Q3
select quarterex("20191001020202");
-- 2019Q3
select quarterex("2019-12-31 16:24:39 CST");
-- 2019Q4
select quarterex("2019-12-31 16:25:34 Thu");
-- 2019Q4
select quarterex(CAST('2018-06-05' AS DATE));
-- 2018Q2
select quarterex(CAST('2018-06-05 16:24:39' AS TIMESTAMP));
-- 2018Q2
select quarterex("20190101", "yyyyQR");
-- 2019Q1
select quarterex("2019-03-31", "yyyy-QR");
-- 2019-Q1
select quarterex("2019/04/01", "yyyy_QR");
-- 2019_Q2
select quarterex("2019年06月30日", "yyyy/QR");
-- 2019/Q2
select quarterex("2019-07-01 11:21:30", "yyyyqr");
-- 20193
select quarterex("20191001020202", "yyyy-qr");
-- 2019-4
select quarterex("2019-12-31 16:24:39 CST", "yyyy_qr");
-- 2019_4
select quarterex("2019-12-31 16:25:34 Thu", "yyyy");
-- 2019
select quarterex(CAST('2018-06-05' AS DATE), "qr");
-- 2
select quarterex(CAST('2018-06-05 16:24:39' AS TIMESTAMP), "yyyy年QR");
-- 2018年Q2
select quarterex(CAST('2018-06-05 16:24:39' AS TIMESTAMP), "yyyy年第qr季度");
-- 2018年第2季度
select quarterex(CAST('2018-06-05 16:24:39' AS TIMESTAMP), "年:yyyy 季度:qr");
-- 年:2018 季度:2

-- -----------------------------------------------------------------------
-- UDFClearText
-- -----------------------------------------------------------------------
select clear_text(null);
-- (NOTHING)
select clear_text("１　3１A３ｂ５６ 7！８　9");
-- 131A3b567!89
select clear_text("1３1 23４ 5A　6 b7ｃ8　9");
-- 1312345A6b7c89
select clear_text("１-AＤ 1　1 01");
-- 1-AD1101

-- -----------------------------------------------------------------------
-- UDFToDate
-- ONE PARAMETER : "yyyy-MM-dd HH:mm:ss zzz"
                   "yyyy-MM-dd HH:mm:ss EE"
                   "yyyy-MM-dd HH:mm:ss"
                   "yyyy-MM-dd HH:mm"
                   "yyyy-MM-dd"
                   "yyyyMMddHHmmss"
                   "yyyyMMdd"
                   "yyyy年MM月dd日"
                   "yyyy/MM/dd"
-- TWO PARAMETER : see [http://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html]
-- -----------------------------------------------------------------------
select to_date2(null);
-- (NULL)
select to_date2(null, 'yyyy-MM-dd');
--  (NULL)
select to_date2("20190101");
-- 2019-01-01
select to_date2("2019-03-31");
-- 2019-03-31
select to_date2("2019/04/01");
-- 2019-04-01
select to_date2("2019年06月30日");
-- 2019-06-30
select to_date2("2019-07-01 11:21:30");
-- 2019-07-01
select to_date2("20191001020202");
-- 2019-10-01
select to_date2("2019-12-31 16:24:39 CST");
-- 2019-12-31
select to_date2("2019-12-31 16:25:34 Thu");
-- 2019-12-31
select to_date2("19年06月30日", "yy年MM月dd日");
-- 2019-06-30
select to_date2("31/12/2019", "dd/MM/yyyy");
-- 2019-12-31
select to_date2("02.17.2017", "MM.dd.yyyy");
-- 2017-02-17
 
```
