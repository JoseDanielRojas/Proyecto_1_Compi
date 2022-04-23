/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Pablo
 */
public class ChooseCommand extends Command {

    public ChooseCommand(Expression EAST,Cases CasesAST, Command cAST,
                    SourcePosition thePosition) {
    super (thePosition);
    E = EAST;
    Cas=CasesAST;
    C = cAST;
  }

    @Override
    public Object visit(Visitor v, Object o) {
      return v.visitChooseCommand(this,o);
    }

    public Expression E;
    public Cases Cas;
    public Command C;
}
