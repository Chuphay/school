;;; problem 3
(define PI 3.14159265359)
(define (circle-space r)
  (* PI r r)
)
(display (= (circle-space 1) PI))
(display (= (circle-space 2) (* 4 PI)))
(display "\n")

;;; problem 4

(define (temp-change f)
  (* (- f 32) (/ 5. 9))
)

(display (= (temp-change 68) 20))
(display (= (temp-change 104) 40))
(display "\n")

;;; problem 5

;;; apparently racket has a procedure that returns last
;;; but my version of scheme doesn't have this, so we'll have to build it

(define (last l)
  (if (= (length l) 1)
      (car l)
      (last (cdr l))
      )
)
(define (front-or-back l)
  (if (= (remainder (length l) 2) 0)
      (car l)
      (last l)
      )
)
(display (= (front-or-back '(1 2 3 4)) 1))
(display (= (front-or-back '(1 2 3 4 5)) 5))
(display "\n")



