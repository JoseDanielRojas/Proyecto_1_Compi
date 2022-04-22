/*
 * @(#)Token.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved. 
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;


final class Token extends Object {

  protected int kind;
  protected String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if (kind == Token.IDENTIFIER) {
      int currentKind = firstReservedWord;
      boolean searching = true;

      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,

    // reserved words - must be in alphabetical order...
    // se agrega el token de AND 
    AND                 = 4,          
    ARRAY		= 5,
    // se elimina la palabra begin
    // BEGIN		= 5,
    // se agrega el token de choose
    CHOOSE              = 6,
    CONST		= 7,
    DO			= 8,
     // se agrega el token de ELSIF
    ELSE               = 9,
    ELSIF		= 10,
    END			= 11,
     // se agrega el token de FOR Y FROM
    FOR                 = 12,
    FROM                = 13,
    FUNC		= 14,
    IF			= 15,
    IN			= 16,
    LET			= 17,
    // se agrega el token de nothing
    NOTHING             = 18,
    OF			= 19,
     // se agrega el token de PRIVATE
    PRIVATE             = 20,
    PROC		= 21,
    RECORD		= 22,
    // se agrega el token de RECURSIVE Y REPEAT
    RECURSIVE           = 23,
    REPEAT              = 24,
    THEN		= 25,
    TYPE		= 26,
    // se agrega el token de UNTIL
    UNTIL               = 27,
    VAR			= 28,
    // se agrega el token de WHEN
    WHEN                = 29,
    WHILE		= 30,

    // punctuation...
    DOT			= 31,
    DOBLEDOT            = 32,
    COLON		= 33,
    SEMICOLON	        = 34,
    COMMA		= 35,
    BECOMES		= 36,
    IS			= 37,

    // brackets...
    LPAREN		= 38,
    RPAREN		= 39,
    LBRACKET	= 40,
    RBRACKET	= 41,
    LCURLY		= 42,
    RCURLY		= 43,

    // special tokens...
    EOT			= 44,
    ERROR		= 45;

  private static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<operator>",
    
    "and",
    "array",
    
    //"begin",
    
    "choose",
    "const",
    "do",
    
    "else",
    "elsif",
    "end",
    
    "for",
    "from",
    "func",
    "if",
    "in",
    "let",
    
    "nothing",
    "of",
    
    "private",
    "proc",
    "record",
    
    "recursive",
    "repeat",
    "then",
    "type",
    
    "until",
    "var",
    "when",
    "while",
  
    ".",
    "..",
    ":",
    ";",
    ",",
    ":=",
    "~",
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "",
    "<error>"
  };

  private final static int	firstReservedWord = Token.ARRAY,
  				lastReservedWord  = Token.WHILE;

}
