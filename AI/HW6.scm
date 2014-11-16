;;; problem 1
; a) valid
; b) satisfiable
; c) valid
; d) unsatisfiable
; e) valid
; f) satisfiable
; g) satisfiable
; h) valid

;;; problem 2
;
; (A ^ (B v C))
; if A is false then the statement is false
; (A ^ B) v (A ^ C)
; this is also false when A is false
;
; if A is true, then if both B and C are false 
; the statement is false
; (A ^ B) v (A ^ C)
; this is also false when A is false
; 
; if A is true and either B or C are true
; then the statement is true
; (A ^ B) v (A ^ C)
; this is also true when A is true and either B or C is true


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



