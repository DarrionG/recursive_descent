import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class recursive_descent_parser {
    //Global declarations
    private static int charClass;
    private static String lexeme;
    private static char nextChar;
    private static int lexLen;
    private static int nextToken;
    private static String buffer;
    private static int program_line;
    private static BufferedReader reader;
    //Character classes
    private static final int LETTER = 0;            //letter -> a-z | A-Z
    private static final int DIGIT = 1;             //Digit -> 0-9
    private static final int UNKNOWN = 99;          //Not recognized
    //Token codes
    //Itentifiers
    private static final int ID = 10;               //ID -> Letter | letter ID
    //Float
    private static final int DECIMAL = 11;          //Decimal -> .
    private static final int FLOAT = 12;            //Float -> 
    //Operators
    private static final int ADD_OP = 20;           //Add_op -> +
    private static final int SUB_OP = 21;           //Sub_op -> -
    private static final int MULT_OP = 22;          //Must_op -> *
    private static final int DIV_OP = 23;           //Div_op -> /
    private static final int MOD_OP = 24;           //Mod_op -> %
    private static final int EQUAL_OP = 25;         //Equal_op -> =
    private static final int NEQUAL_OP = 26;        //Nequal_op -> <>
    private static final int GRE_THAN = 27;         //Gre_than -> >
    private static final int LES_THAN = 28;         //Les_than -> <
    private static final int ASSIGN = 29;           //Assign -> :=
    private static final int RIGHT_PAREN = 30;      //Right_paren -> (
    private static final int LEFT_PAREN = 31;       //Left_paren -> )
    //Curly Bois
    private static final int RIGHT_CURLY = 32;      //Right_curly -> {
    private static final int LEFT_CURLY = 33;       //Left_curly -> }
    //Termination
    private static final int END_STATEMENT = 34;    //End of statement -> ;
    //Declare
    private static final int DECLARE = 35;          //Declare -> | :
    private static final int SEPARATE = 36;         //Separate -> ,
    private static final int INPUT = 37;            //Input -> <<
    private static final int OUTPUT = 38;           //Output -> <<
    //Reservered Words
    private static final int RW_PROGRAM = 39;       //Reserved -> program | begin | end | if | then | else | input | output | int | while | loop
    private static final int RW_BEGIN = 40;
    private static final int RW_END = 41;
    private static final int RW_IF = 42;
    private static final int RW_THEN = 43;
    private static final int RW_ELSE = 44;
    private static final int RW_INPUT = 45;
    private static final int RW_OUTPUT = 46;
    private static final int RW_INT = 47;
    private static final int RW_WHILE = 48;
    private static final int RW_LOOP = 49;
    private static final int RW_REPEAT = 50;
    private static final int RW_CIN = 51;
    private static final int RW_COUT = 52;
    //EOF
    private static final int EOF = 100;         //Eof -> end-of-file
    public static void readFileLine() {
        try {
            while (buffer == null || buffer.isEmpty()){
                buffer = reader.readLine();
                program_line++;
                
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void addChar() {
        if(lexLen <= 98) {
                lexeme = lexeme + nextChar;
        }
        else {
            System.out.println("Error - Lexeme too long");
            System.exit(0);
        }
        buffer = buffer.substring(1);
    }
    static void getChar() {
        buffer = buffer.trim();
        if (lexLen != buffer.length()){
            nextChar = buffer.charAt(0);
            if (Character.isAlphabetic(nextChar)){
                charClass = LETTER;
            }
            else if (Character.isDigit(nextChar)){
                charClass = DIGIT;
            }
            else if (nextChar == '<'){
                charClass = LES_THAN;
            }
            else if (nextChar == '>'){
                charClass = GRE_THAN;
            }
            else if (nextChar == ':'){
                charClass = DECLARE;
            }
            else if (nextChar == '='){
                charClass = EQUAL_OP;
            }
            else if (nextChar == '.'){
                charClass = DECIMAL;
            }
            else { 
                charClass = UNKNOWN;
            }
        }
        else {
            charClass = EOF;
        }
    }
    //Lookup unknown characters
    public static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '=':
                addChar();
                nextToken = EQUAL_OP;
                break;
            case ';':
                addChar();
                nextToken = END_STATEMENT;
                break;
            case ',':
                addChar();
                nextToken = SEPARATE;
                break;
            case '.':
                addChar();
                nextToken = DECIMAL;
                break;
            case '{':
                addChar();
                nextToken = LEFT_CURLY;
                break;
            case '}':
                addChar();
                nextToken = RIGHT_CURLY;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '%':
                addChar();
                nextToken = MOD_OP;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }
    //Reserved word table
    public static int reservedWords (String word) {
        switch(word){
            case "program":
                nextToken = RW_PROGRAM;
                break;
            case "begin":
                nextToken = RW_BEGIN;
                break;
            case "end":
                nextToken = RW_END;
                break;
            case "then":
                nextToken = RW_THEN;
                break;
            case "if":
                nextToken = RW_IF;
                break;
            case "else":
                nextToken = RW_ELSE;
                break;
            case "input":
                nextToken = RW_INPUT;
                break;
            case "output":
                nextToken = RW_OUTPUT;
                break;
            case "int":
                nextToken = RW_INT;
                break;
            case "while":
                nextToken = RW_WHILE;
                break;
            case "loop":
                nextToken = RW_LOOP;
                break;
            case "repeat":
                nextToken = RW_REPEAT;
                break;
            case "cin":
                nextToken = RW_CIN;
                break;
            case "cout":
                nextToken = RW_COUT;
                break;
            default:
                break;
        }
        return nextToken;
    }
    public static int lex() {

        if (buffer == null || buffer.isEmpty()) {
            readFileLine();
            getChar();
        }

        lexLen = 0;
        lexeme = "";
        switch (charClass) {
            //Rule 05: ID -> (a|b|c|...| z | A | ... | Z)+
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER){
                    addChar();
                    getChar();
                }
                nextToken = ID;
                reservedWords(lexeme);
                break;
            //Rule 17: FLOAT -> (0 | 1 | ... | 9)+ . (0 | 1| … | 9)+
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT){
                    addChar();
                    getChar();
                }
                if (charClass != DECIMAL){
                    System.out.println("invalid float at line: " + program_line);
                    System.exit(0);
                }
                else {
                    addChar();
                    getChar();
                }
                addChar();
                getChar();
                if (charClass != DIGIT){
                    System.out.println("invalid float at line: " + program_line);
                    System.exit(0);
                }
                else {
                    while (charClass == DIGIT){
                        addChar();
                        getChar();
                    }
                }
                nextToken = FLOAT;
                break;
            //Check for << or <>
            case LES_THAN:
                addChar();
                getChar();
                if (charClass == LES_THAN){
                    addChar();
                    getChar();
                    nextToken = INPUT;
                }
                else if (charClass == GRE_THAN){
                    addChar();
                    getChar();
                    nextToken = NEQUAL_OP;
                }
                else {
                    nextToken = LES_THAN;
                }
                break;
            //Check for >>
            case GRE_THAN:
                addChar();
                getChar();
                if (charClass == GRE_THAN){
                    addChar();
                    getChar();
                    nextToken = OUTPUT;
                }
                else {
                    nextToken = GRE_THAN;
                }
                break;
            //Check for :=
            case DECLARE:
                addChar();
                getChar();
                if (charClass == EQUAL_OP){
                    addChar();
                    getChar();
                    nextToken = ASSIGN;
                }
                else {
                    nextToken = DECLARE;
                }
                break;
            //Any other case
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
        }
        return nextToken;
    }

    //Rule 01: PROGRAM -> program { DECL_SEC STMT_SEC }
    static void program () {
        //Get next token
        lex();
        //Check for reserved word "program"
        if (nextToken != RW_PROGRAM) {
            error();
        }
        else {
            //Get next token
            lex();
            //Check for "{"
            if ( nextToken != LEFT_CURLY) {
                error();
            }
            else {
                //Check DECL_SEC
                decl_sec();
                //Check STMT_SEC
                stmt_sec();
            }
            //Get next token
            lex();
            //Check for "}"
            if (nextToken != RIGHT_CURLY) {
                error();
            }
        }
    }
    //Rule 02: DECL_SEC -> DECL | DECL DECL_SEC
    static void decl_sec () {
        //Check DECL
        decl();
    }
    //Rule 03: DECL -> ID_LIST : Float;
    static void decl () {
        //Check ID_LIST
        id_list();
        //Check for ":"
        if (nextToken != DECLARE) {
            error();
        }
        else{
            //Get next token
            lex();
            //Check for "Float"
            if (nextToken != ID && lexeme != "Float") {
                error();
            }
            else {
                //Get next token
                lex();
                //Check for ";"
                if (nextToken != END_STATEMENT) {
                    error();
                }
            }
        }
    }
    //Rule 04: ID_LIST -> ID | ID , ID_LIST
    static void id_list () {
        //Get next token
        lex();
        //Check for ID
        if (nextToken != ID) {
            error();
        }
        else {
            //Get next token
            lex();
            //Check for ","
            if (nextToken == SEPARATE) {
                id_list();
            }
        }
    }
    //Rule 06: STMT_SEC -> STMT | STMT STMT_SEC
    static void stmt_sec () {
        //Check STMT
        stmt();
    }
    //Rule 07: STMT ->  ASSIGN | IFSTMT | WHILESTMT | REPEAT | INPUT | OUTPUT
    static void stmt () {
        //Get next token
        lex();
        switch(nextToken){
            //Check for "if"
            case RW_IF:
                ifstmt();
                stmt_sec();
                break;
            //Check for "while"
            case RW_WHILE:
                whilestmt();
                stmt_sec();
                break;
            //Check ASSIGN
            case ID:
                assign();
                stmt_sec();
                break;
            //Check for "repeat"
            case RW_REPEAT:
                repeat();
                stmt_sec();
                break;
            //Check for "cin"
            case RW_CIN:
                input();
                stmt_sec();
                break;
            //Check for "cout"
            case RW_COUT:
                output();
                stmt_sec();
                break;
        }

    }
    //Rule 08: ASSIGN ->  ID := EXPR;
    static void assign () {
        //Get next token
        lex();
        //Check for ":="
        if (nextToken != ASSIGN){
            error();
        }
        else{
            //Check EXPR
            expr();
            //Get next token
            if (nextToken != END_STATEMENT){
                error();
            }
        }

    }
    //Rule 09: IFSTMT ->  if COMP { STMT_SEC } | if COMP { STMT_SEC } else { STMT_SEC } 
    static void ifstmt () {
        //Check COMP
        comp();
        //Get next token
        lex();
        //Check for "{"
        if (nextToken != LEFT_CURLY){
            error();
        }
        else {
            //Check STMT_SEC
            stmt_sec();
            //Check for "}"
            if (nextToken != RIGHT_CURLY){
                error();
            }
            else {
                //Get next token
                lex();
                //Check for "else"
                if (nextToken == RW_ELSE){
                    //Get next token
                    lex();
                    //Check for "{"
                    if (nextToken != LEFT_CURLY){
                        error();
                    }
                    else {
                        //Check STMT_SEC
                        stmt_sec();
                        //Chck for "}"
                        if (nextToken != RIGHT_CURLY){
                            error();
                        }
                    }
                }
            }
        }
    }
    //Rule 10: WHILESTMT ->  while COMP { STMT_SEC }
    static void whilestmt () {
        //Check COMP
        comp();
        //Get next token
        lex();
        //Check "{"
        if (nextToken != LEFT_CURLY){
            error();
        }
        else {
            //Check STMT_SEC
            stmt_sec();
            //Check for "}"
            if (nextToken != RIGHT_CURLY){
                error();
            }
        }
    }
    //Rule 11: REPEAT -> repeat { STMT_SEC } until COMP;
    static void repeat () {
        //Get next token
        lex();
        //Check for "{"
        if (nextToken != LEFT_CURLY){
            error();
        }
        else{
            //Check STMT_SEC
            stmt_sec();
            //Check for "{"
            if(nextToken != RIGHT_CURLY){
                error();
            }
            else{
                //Get next token
                lex();
                //Check for "until"
                if (nextToken != ID && lexeme != "until"){
                    error();
                }
                else{
                    //Check COMP
                    comp();
                    //Get next token
                    lex();
                    //Check for ";"
                    if(nextToken != END_STATEMENT){
                        error();
                    }
                }
            }
        }
    }
    //Rule 12: INPUT ->  cin << ID_LIST;
    static void input () {
        //Get next token
        lex();
        //Check for "<<"
        if (nextToken != INPUT){
            error();
        }
        else{
            //Check ID_LIST
            id_list();
            //Check for ";"
            if(nextToken != END_STATEMENT){
                error();
            }
        }
    }
    //Rule 13: OUTPUT -> cout >> ID_LIST;
    static void output () {
        //Get next token
        lex();
        //Check for ">>"
        if (nextToken != OUTPUT){
            error();
        }
        else{
            //Check ID_LIST
            id_list();
            //Check for ";"
            if(nextToken != END_STATEMENT){
                error();
            }
        }
    }
    //Rule 14: EXPR -> FACTOR | FACTOR + EXPR | FACTOR - EXPR
    static void expr () {
        //Check FACTOR
        factor();
        //Check for + or -
        if (nextToken == ADD_OP || nextToken == SUB_OP){
            //Check EXPR
            expr();
        }
    }
    //Rule 15: FACTOR -> OPERAND | OPERAND * FACTOR | OPERAND / FACTOR | OPERAND % FACTOR
    static void factor () {
        //Check OPERAND
        operand();
        //Get next token
        lex();
        //Check for *
        if (nextToken == MULT_OP || nextToken == DIV_OP || nextToken == MOD_OP){
            //Check FACTOR
            factor();
        }
    }
    //Rule 16: OPERAND -> FLOAT | ID | ( EXPR )
    static void operand () {
        //Get next token
        lex();
        //Check for "("
        if (nextToken == LEFT_PAREN) {
            //Check EXPR
            expr();
            //Check for ")"
            if (nextToken != RIGHT_PAREN){
                error();
            }
        }
        else {
            //Check for FLOAT or ID
            if (nextToken != FLOAT && nextToken != ID){
                error();
            } 
        }
    }
    //Rule 18: COMP -> ( OPERAND = OPERAND ) | ( OPERAND <> OPERAND ) | ( OPERAND > OPERAND ) | ( OPERAND < OPERAND ) 
    static void comp () {
        //Get next token
        lex();
        //Check for "("
        if (nextToken != LEFT_PAREN) {
            error();
        }
        else {
            //Check OPERAND
            operand();
            //Get next token
            lex();
            //Check for "=", "<>", ">", or "<"
            if (nextToken != EQUAL_OP && nextToken != NEQUAL_OP && nextToken != GRE_THAN && nextToken != LES_THAN) {
                error();
            }
            else {
                //Check OPERAND
                operand();
                //Get next token
                lex();
                //Check for ")"
                if (nextToken != RIGHT_PAREN) {
                    error();
                }
            }
        }
    }
    static void error () {
        System.out.println("Error on line: " + program_line + " Invalid token: " + nextToken + " lexeme: " + lexeme);
        System.exit(0);
    }
    public static void main(String[] args) {
        program_line = 0;
        try {
            reader = new BufferedReader(new FileReader("program.txt"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        program();
    }
}
