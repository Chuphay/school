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
needles_more_than_six_in_bunch(yes).
% needles_three_sided(neg). 



% http://stackoverflow.com/questions/5107745/user-input-how-can-we-do-it
% http://stackoverflow.com/questions/9564614/prolog-expert-system-is-so-hard 

ask(A,V):-
  write(A:V),                       
  write('? (yes or no): '),
  read(Y),                         
  (asserta(known(Y,A,V)),           % this remembers the answer, very useful for a nice user interface
     Y = yes
  ). 


%ask(Attr, Val):- 
% write(Attr:Val), 
% write('? '), 
% read(yes). 

needles_three_sided(X) :- ask(needles_three_sided, X).
