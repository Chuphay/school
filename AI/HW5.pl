family([helen,milton],[[liz, sarah],[paul,john]]).
family([marcelia,chuphay],[[suzanna, cathy],[kippy]]).
family([suzanna,john],[[lily],[david,michael]]).



father_of(D,C) :- family([_,D],[Ds,Ss]),
                  (member(C,Ds);
                   member(C,Ss)).
mother_of(D,C) :- family([D, _],[Ds,Ss]),
                  (member(C,Ds);
                   member(C,Ss)).

parent_of(P,C) :- father_of(P,C).
parent_of(P,C) :- mother_of(P,C).

male(M) :- family([_,M],_).
male(M) :- family(_,[_,Ss]), member(M,Ss).

member(M,[M|_]).               	  % base case
member(M,[_|T]) :- member(M,T).   % recursive case


son_of(X,Y)  :- (family([_,Y],[_,Ss]),
                   member(X,Ss);
		family([Y,_],[_,Ss]),
                   member(X,Ss)).   

daughter_of(X,Y) :- (family([_,Y],[Ds,_]),
		     member(X,Ds);
		  family([Y,_],[Ds,_]),
		     member(X,Ds)).

     
brother_of(X,Y) :- family([_,_],[Ds,Ss]),
     (member(Y,Ss); member(Y,Ds)), member(X,Ss).

sister_of(X,Y) :- family([_,_],[Ds,Ss]),
     (member(Y,Ss); member(Y,Ds)), member(X,Ds).

sibling_of(X,Y) :-  sister_of(X,Y);brother_of(X,Y).

grandparent_of(X,Y) :- (father_of(Z,Y),(son_of(Z,X);daughter_of(Z,X)); 
			mother_of(Z,Y),(son_of(Z,X);daughter_of(Z,X))).    

ancestor(X,Y) :- parent_of(X,Y).
ancestor(X,Y) :- parent_of(Z,Y) , ancestor(X,Z).


getFirst([H|_], H) .

oldest_son(S,P) :- (family([_,P],[_,Ss]),getFirst(Ss,S);family([P,_],[_,Ss]),getFirst(Ss,S)).



oldest_daughter(D,P) :- (family([_,P],[Ds,_]),getFirst(Ds,D);family([P,_],[Ds,_]),getFirst(Ds,D)).    




