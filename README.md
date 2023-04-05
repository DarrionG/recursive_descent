# Recursive Descent Parser
A recursive descent parser that produces a parse tree. This project is used only as a way
to understand and learn about some of the components that make up a compiler. This is not
designed to be a functional language.

## Language Grammer

Rule 01: PROGRAM -> program { DECL_SEC STMT_SEC }
Rule 02: DECL_SEC -> DECL | DECL DECL_SEC
Rule 03: DECL -> ID_LIST : Float;
Rule 04: ID_LIST -> ID | ID , ID_LIST
Rule 05: ID -> (a|b|c|...| z | A | ... | Z)+
Rule 06: STMT_SEC -> STMT | STMT STMT_SEC
Rule 07: STMT ->  ASSIGN | IFSTMT | WHILESTMT | REPEAT | INPUT | OUTPUT
Rule 08: ASSIGN ->  ID := EXPR ;
Rule 09: IFSTMT ->  if COMP { STMT_SEC } |
                   if COMP { STMT_SEC } else { STMT_SEC } 
Rule 10: WHILESTMT ->  while COMP { STMT_SEC }
Rule 11: REPEAT -> repeat { STMT_SEC } until COMP ;
Rule 12: INPUT ->  cin << ID_LIST ;
Rule 13: OUTPUT -> cout >> ID_LIST ;
Rule 14: EXPR -> FACTOR | FACTOR + EXPR | FACTOR - EXPR 
Rule 15: FACTOR -> OPERAND | OPERAND * FACTOR | OPERAND / FACTOR |
         OPERAND % FACTOR
Rule 16: OPERAND -> FLOAT | ID | ( EXPR )
Rule 17: FLOAT -> (0 | 1 | ... | 9)+ . (0 | 1| … | 9)+
Rule 18: COMP -> ( OPERAND = OPERAND ) | ( OPERAND <> OPERAND ) |
         ( OPERAND > OPERAND ) | ( OPERAND < OPERAND ) 

## Lexemes

- Reserved words: program, begin, end, if, then, else, input, output,
                    Float, while, loop.
- Operators: := (assignment), < (less than), > (greater than), = (equals),
                <> (not equals), + (add), - (subtract), * (multiply).
- Other: ; (end statment).
