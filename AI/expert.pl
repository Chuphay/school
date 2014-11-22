tree(western_larch) :- 
leaves(needles),
needles_in_bunch(yes),
needles_more_than_six_in_bunch(yes),
needles_three_sided(yes). 

tree(subalpine_larch) :- 
leaves(needles),
needles_in_bunch(yes),
needles_more_than_six_in_bunch(yes),
needles_three_sided(neg). 
  
leaves(needles).
needles_in_bunch(yes).
% needles_more_than_six_in_bunch(yes).
% needles_three_sided(neg). 



% http://stackoverflow.com/questions/5107745/user-input-how-can-we-do-it
% http://stackoverflow.com/questions/9564614/prolog-expert-system-is-so-hard 

/*
ask(A,V):-
  write(A:V),                       
  write('? (yes or no): '),
  read(Y),                         
  (asserta(known(Y,A,V)),           % this remembers the answer, very useful for a nice user interface
     Y = yes
  ). 
*/

% http://swi-prolog.996271.n3.nabble.com/Undefined-Procedure-Help-td8386.html
% http://www.inf.fu-berlin.de/lehre/SS09/KI/folien/merritt.pdf

:- dynamic(known/3). 


ask(A, V):-
known(yes, A, V), % succeed if true
!.	% stop looking

ask(A, V):-
known(_, A, V), % fail if false
!, fail.


ask(A, V):-
write(A:V), % ask user
write('? : '), 
read(Y), % get the answer
asserta(known(Y, A, V)), % remember it
Y == yes.	% succeed or fail



%ask(Attr, Val):- 
% write(Attr:Val), 
% write('? '), 
% read(yes). 

needles_three_sided(X) :- ask(needles_three_sided, X).
needles_more_than_six_in_bunch(X) :- ask(needles_more_than_six_in_bunch, X).
