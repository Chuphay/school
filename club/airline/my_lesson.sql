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
