/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author MSI
 */
public class ArrayDeclarationOF extends ArrayDeclaration {
    public ArrayDeclarationOF (TypeDenoter eAST,
                        SourcePosition thePosition) {
    super (thePosition);
    E = eAST;
  }

    @Override
  public Object visit (Visitor v, Object o) {
    return v.visitArrayDeclarationOF(this, o);
  }

  public TypeDenoter E;
    
}

