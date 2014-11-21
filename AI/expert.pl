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


C := 5.
info([A,B,C]).


% http://stackoverflow.com/questions/9564614/prolog-expert-system-is-so-hard 
ask(A,V):-
  write(A:V),                       % if we get here, we need to ask.
  write('? (yes or no): '),
  read(Y),                          % get the answer
  (asserta(known(Y,A,V)),           % remember it so we dont ask again.
     Y = yes
  ). 


%ask(Attr, Val):- 
% write(Attr:Val), 
% write('? '), 
% read(yes). 

needles_three_sided(X) :- ask(needles_three_sided, X).
