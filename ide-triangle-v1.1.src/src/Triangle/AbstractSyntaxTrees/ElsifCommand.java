/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class ElsifCommand extends Command {

  public ElsifCommand (Expression eAST, Command c1AST,
                    SourcePosition thePosition) {
    super (thePosition);
    E = eAST;
    C = c1AST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitElsifCommand(this, o);
  }

  public Expression E;
  public Command C;
}
