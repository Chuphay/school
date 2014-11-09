
male(milton).
male(chuphay). 
male(john). 
male(david). 
male(michael). 

female(helen).
female(marcelia).
female(cathy). 
female(suzanna). 
female(lily).

parent_of(david, suzanna).
parent_of(david, john).
parent_of(lily, suzanna).
parent_of(lily, john).
parent_of(michael, suzanna).
parent_of(michael, john).
parent_of(john, milton).
parent_of(john,helen).
parent_of(suzanna, chuphay).
parent_of(suzanna, marcelia).
parent_of(cathy, chuphay).
parent_of(cathy, marcelia).



child_of(Y,X) :- parent_of(X,Y).
daughter_of(X,Y) :- child_of(X,Y), female(Y).
son_of(X,Y) :- child_of(X,Y), male(Y).
father_of(X,Y) :- parent_of(X,Y), male(Y).
mother_of(X,Y) :- parent_of(X,Y), female(Y).
sibling_of(X,Y) :- parent_of(X,Z), parent_of(Y,Z).
brother_of(X,Y) :- sibling_of(X,Y), male(Y).
sister_of(X,Y) :- sibling_of(X,Y), female(Y).
grandparent_of(X,Y) :- parent_of(X,W), parent_of(W,Y).


% I did this first with an or-structure
ancestor_of(X,Y) :- (grandparent_of(X,Y); parent_of(X,Y)).

% then I saw that you wanted it done recursively,
% so I did it this way: 
ancestor(X,Y) :- parent_of(X,Y).
ancestor(X,Y) :- parent_of(Z,Y) , ancestor(X,Z).


