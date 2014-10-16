;;;Problem 1

(define (chk x)
  (if (> x 0)
      1 
      (if (= x 0)
	  0
	  -1
      )
  )
)
(display (= (chk 32) 1)) 
(display (= (chk -32) -1))
(display (= (chk 0) 0))
(display "\n")

;;;Problem 2

(define (get_el lst x)
  (if (= (length lst) 0)
      #f
      (if (= (car lst) x)
	  x
	  (get_el (cdr lst) x)
      )
  )
)

(display (= (get_el '(1 2 5) 5) 5))
(display (= (get_el '(1 2 5) 1) 1))
(display (equal? (get_el '(1 2 5) 6) #f))
(display "\n")

;;;Problem 3

(define (el_or_lst lst x)
  (if (equal? (get_el lst x) #f)
      lst
      x
  )
)

(display (= (el_or_lst '(1 2 5 6) 5) 5)) 
(display (= (el_or_lst '(1 2 5 6) 2) 2)) 
(display (equal? (el_or_lst '(1 2 5 6) 7) '(1 2 5 6)))
(display "\n") 
