/*To run this script try: $ sqlite3 airline.db < my_lesson.sql */

/*Question 1*/
SELECT * FROM ontime LIMIT 1;
/*Now, you guys try, but try to do a limit 3 or some other number*/

/*Question 2*/
/*Now we need to find out what all these things mean, 
we can do this in two ways */
.schema
.header on
SELECT * FROM ontime LIMIT 1;

/*Question 3 */
/*Let's take a look at only one*/
SELECT ArrDelay FROM ontime LIMIT 10;
/*Try on some other interesting ones.*/

/*Question 4 */
/*only one is too restricting, lets get more*/
SELECT ArrDelay, Origin FROM ontime LIMIT 10;

SELECT avg(ArrDelay) FROM ontime;

SELECT sum(ArrDelay > 0) FROM ontime;

SELECT avg(ArrDelay > 0) FROM ontime;

SELECT min(ArrDelay) FROM ontime;

SELECT sum(ArrDelay < -500) FROM ontime;

SELECT count(*) FROM ontime;





/*Question 5: Let's make a view because it'll be a lot easier. */
/*Let's concentrate on the numerical columns first */
CREATE VIEW test2 AS SELECT CRSDepTime, DepTime, DepDelay, CRSElapsedTime, 
ActualElapsedTime, CRSArrTime, ArrTime, ArrDelay 
FROM ontime;
