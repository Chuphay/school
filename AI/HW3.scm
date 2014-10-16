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
