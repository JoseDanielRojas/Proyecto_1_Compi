/*
 * @(#)IfCommand.java                        2.1 2003/10/07
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

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class ForCommand extends Command {

  public ForCommand (Identifier iAST, Expression e1AST, Expression e2AST,Command cAST,
                    SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    E1 = e1AST;
    E2 = e2AST;
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitForCommand(this, o);
  }

  public Expression E1,E2;
  public Identifier I;
  public Command C;
}
