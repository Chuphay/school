(define (abs x)
  (if (< x 0)
      (- x)
      x
  )
)

(define (check_equality x y tolerance)
  (if (< (abs (- x y)) tolerance)
    #t
    #f
  )
)

(define (newtons_sqrt x g)                 
  (if (check_equality x (* g g) 0.001)
      g
      (newtons_sqrt x (/ (+ g (/ x g)) 2))
  )
)

;;;Unit Tests
(display (= (abs 0) 0))
(display (= (abs -3) 3))
(display (= (abs 5) 5))
(display "\n")
(display  (= (newtons_sqrt 2 1) 577/408))
(display (check_equality (newtons_sqrt 2 1.) 1.41421 0.001))
(display (check_equality (newtons_sqrt 4 -3.) -2. 0.001) )

(display "\n")
