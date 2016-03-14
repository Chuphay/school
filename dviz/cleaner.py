import sys

i = 0
for line in sys.stdin:
    i += 1
    if(i<30000):
        print(line, end ="")
    else:
        break
