/*Let's make sure everyone is on the same page*/

SELECT * FROM ontime LIMIT 1;

/* Check to make sure that people delted that first line */
/*It should show:
1988|1|9|6|1348|1331|1458|1435|PI|942|NA|70|64|NA|23|17|SYR|BWI|273|NA|NA|0|NA|0|NA|NA|NA|NA|NA
*/
/*If I haven't done a good job of explaining K-NN, then you will probably have to explain this code.
If I've done a good job of explaining K-NN, you will still have to explain
the following code.
But I think, rather than having them type in the code, let's let them copy from a website. I'll post something soon*/
.header on

SELECT (Month=1) + (DayofMonth=24) + (DayofWeek=2) + 
(abs(CRSDepTime - 1850) < 20) + (abs(CRSElapsedTime - 46) < 20) +
(UniqueCarrier = "NW") + (FlightNum = 347) + (Origin = "MBS") + (Dest = "DTW") 
as Similarity, DepDelay, ArrDelay FROM ontime ORDER BY Similarity DESC LIMIT 15;

/* I guess an important thing to notice is that there are 9 things to set equal, but the top 15, all have 7 and less. This is actually good, because that means weighting things in different ways will have a big effect*/

/*Personally I think month and flightNum are useless, but I think Deptime and origin are super usefull*/


SELECT 0.4*(Month=1) + 1.3*(DayofMonth=24) + 0.7*(DayofWeek=2) + 
2.1*(abs(CRSDepTime - 1850) < 20) + 1.6*(abs(CRSElapsedTime - 46) < 20) +
1.5*(UniqueCarrier = "NW") + 0.4*(FlightNum = 347) + 1.9*(Origin = "MBS") + 1.7*(Dest = "DTW") 
as Similarity, DepDelay, ArrDelay FROM ontime ORDER BY Similarity DESC LIMIT 15;

/*Nice a ton of seperation on that last example. Let's play with it a bit*/

/*We are going to make a view, which is the same thing as a table, but a little more temporary*/
/*
CREATE VIEW flt347 AS SELECT 0.4*(Month=1) + 1.3*(DayofMonth=24) + 0.7*(DayofWeek=2) + 
2.1*(abs(CRSDepTime - 1850) < 20) + 1.6*(abs(CRSElapsedTime - 46) < 20) +
1.5*(UniqueCarrier = "NW") + 0.4*(FlightNum = 347) + 1.9*(Origin = "MBS") + 1.7*(Dest = "DTW") 
as Similarity, DepDelay, ArrDelay FROM ontime ORDER BY Similarity DESC LIMIT 15;
*/
SELECT avg(DepDelay), avg(ArrDelay) FROM flt347;

/*Nice that gives us the average DepDelay and ArrDelay from flt347*/
/*We can do some cool other analysis, but let's try one of the flights that has a long delay*/
/*
CREATE VIEW flt515 AS SELECT 0.4*(Month=3) + 1.3*(DayofMonth=23) + 0.7*(DayofWeek=4) + 
2.1*(abs(CRSDepTime - 955) < 20) + 1.6*(abs(CRSElapsedTime - 155) < 20) +
1.5*(UniqueCarrier = "CO") + 0.4*(FlightNum = 515) + 1.9*(Origin = "CLE") + 1.7*(Dest = "TPA") 
as Similarity, DepDelay, ArrDelay FROM ontime ORDER BY Similarity DESC LIMIT 15;
*/
SELECT * FROM flt515;

SELECT avg(DepDelay), avg(ArrDelay) FROM flt515;

/*Now we can do funny weight things. Here we simply weight each entry by its similarity score, and divide by the total similarity score to keep things normalized*/

SELECT sum(DepDelay*Similarity)/sum(Similarity), sum(ArrDelay*Similarity)/sum(Similarity) FROM flt515;

/*We can try it on our first flt as well*/


SELECT sum(DepDelay*Similarity)/sum(Similarity), sum(ArrDelay*Similarity)/sum(Similarity) FROM flt347;

/*If there is still time, let the students try these codes on a different flight*/
/*Or possibly try a different weighting scheme, but I'm tired of doing this. So that's that*/
