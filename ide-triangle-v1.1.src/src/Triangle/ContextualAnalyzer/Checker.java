/*
 * @(#)Checker.java                        2.1 2003/10/07
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

package Triangle.ContextualAnalyzer;

import Triangle.AbstractSyntaxTrees.*;
import Triangle.ErrorReporter;
import Triangle.StdEnvironment;

import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayDeclarationDOBLEDOT;
import Triangle.AbstractSyntaxTrees.ArrayDeclarationOF;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterCase;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.ChooseCommand;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.ElsifCommand;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCase;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForDoCommand;
import Triangle.AbstractSyntaxTrees.ForUntilCommand;
import Triangle.AbstractSyntaxTrees.ForVarDecl;
import Triangle.AbstractSyntaxTrees.ForWhileCommand;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerCase;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.PrivateDeclaration;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.SequentialCase;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.Terminal;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.VarValueDeclaration;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhenCase;
import Triangle.AbstractSyntaxTrees.WhileCommand;

import Triangle.SyntacticAnalyzer.SourcePosition;
import java.util.ArrayList;



public final class Checker implements Visitor {

  // Commands

  // Always returns null. Does not use the given object.
    
  public int globalInt = 0;
    

  public Object visitAssignCommand(AssignCommand ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (!ast.V.variable)
      reporter.reportError ("LHS of assignment is not a variable", "", ast.V.position);
    if (! eType.equals(vType))
      reporter.reportError ("assignment incompatibilty", "", ast.position);
    return null;
  }


  public Object visitCallCommand(CallCommand ast, Object o) {

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared(ast.I);
    else if (binding instanceof ProcDeclaration) {
      ast.APS.visit(this, ((ProcDeclaration) binding).FPS);
    } else if (binding instanceof ProcFormalParameter) {
      ast.APS.visit(this, ((ProcFormalParameter) binding).FPS);
    } else if (binding instanceof ReProcDeclaration) {
      ast.APS.visit(this, ((ReProcDeclaration) binding).FPS);
    }else
      reporter.reportError("\"%\" is not a procedure identifier",
                           ast.I.spelling, ast.I.position);
    return null;
  }

  // no requiere revision contextual. Sebastian Campos
  public Object visitEmptyCommand(EmptyCommand ast, Object o) {
    return null;
  }

  public Object visitIfCommand(IfCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }
  
  
// se revisa igual al commando original de triangulo . Sebastian Campos
  public Object visitLetCommand(LetCommand ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialCommand(SequentialCommand ast, Object o) {
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }

  public Object visitWhileCommand(WhileCommand ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
    ast.C.visit(this, null);
    return null;
  }

  // Expressions

  // Returns the TypeDenoter denoting the type of the expression. Does
  // not use the given object.

  public Object visitArrayExpression(ArrayExpression ast, Object o) {
    ArrayDeclarationOF elemType = (ArrayDeclarationOF) ast.AA.visit(this, null);
    IntegerLiteral il = new IntegerLiteral(new Integer(ast.AA.elemCount).toString(),
                                           ast.position);
    ast.type = new ArrayTypeDenoter(il, elemType, ast.position);
    return ast.type;
  }

  public Object visitBinaryExpression(BinaryExpression ast, Object o) {

    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);

    if (binding == null)
      reportUndeclared(ast.O);
    else {
      if (! (binding instanceof BinaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a binary operator",
                              ast.O.spelling, ast.O.position);
      BinaryOperatorDeclaration bbinding = (BinaryOperatorDeclaration) binding;
      if (bbinding.ARG1 == StdEnvironment.anyType) {
        // this operator must be "=" or "\="
        if (! e1Type.equals(e2Type))
          reporter.reportError ("incompatible argument types for \"%\"",
                                ast.O.spelling, ast.position);
      } else if (! e1Type.equals(bbinding.ARG1))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E1.position);
      else if (! e2Type.equals(bbinding.ARG2))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E2.position);
      ast.type = bbinding.RES;
    }
    return ast.type;
  }

  public Object visitCallExpression(CallExpression ast, Object o) {
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
      reportUndeclared(ast.I);
      ast.type = StdEnvironment.errorType;
    } else if (binding instanceof FuncDeclaration) {
      ast.APS.visit(this, ((FuncDeclaration) binding).FPS);
      ast.type = ((FuncDeclaration) binding).T;
    }else if (binding instanceof ReFuncDeclaration) {
      ast.APS.visit(this, ((ReFuncDeclaration) binding).FPS);
      ast.type = ((ReFuncDeclaration) binding).T;
    } 
    else if (binding instanceof FuncFormalParameter) {
      ast.APS.visit(this, ((FuncFormalParameter) binding).FPS);
      ast.type = ((FuncFormalParameter) binding).T;
    } else
      reporter.reportError("\"%\" is not a function identifier",
                           ast.I.spelling, ast.I.position);
    return ast.type;
  }

  public Object visitCharacterExpression(CharacterExpression ast, Object o) {
    ast.type = StdEnvironment.charType;
    return ast.type;
  }

  public Object visitEmptyExpression(EmptyExpression ast, Object o) {
    ast.type = null;
    return ast.type;
  }

  public Object visitIfExpression(IfExpression ast, Object o) {
    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    if (! e1Type.equals(StdEnvironment.booleanType))
      reporter.reportError ("Boolean expression expected here", "",
                            ast.E1.position);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    TypeDenoter e3Type = (TypeDenoter) ast.E3.visit(this, null);
    if (! e2Type.equals(e3Type))
      reporter.reportError ("incompatible limbs in if-expression", "", ast.position);
    ast.type = e2Type;
    return ast.type;
  }

  public Object visitIntegerExpression(IntegerExpression ast, Object o) {
    ast.type = StdEnvironment.integerType;
    return ast.type;
  }

  public Object visitLetExpression(LetExpression ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.type = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    return ast.type;
  }

  public Object visitRecordExpression(RecordExpression ast, Object o) {
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    ast.type = new RecordTypeDenoter(rType, ast.position);
    return ast.type;
  }

  public Object visitUnaryExpression(UnaryExpression ast, Object o) {

    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);
    if (binding == null) {
      reportUndeclared(ast.O);
      ast.type = StdEnvironment.errorType;
    } else if (! (binding instanceof UnaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a unary operator",
                              ast.O.spelling, ast.O.position);
    else {
      UnaryOperatorDeclaration ubinding = (UnaryOperatorDeclaration) binding;
      if (! eType.equals(ubinding.ARG))
        reporter.reportError ("wrong argument type for \"%\"",
                              ast.O.spelling, ast.O.position);
      ast.type = ubinding.RES;
    }
    return ast.type;
  }

  public Object visitVnameExpression(VnameExpression ast, Object o) {
    ast.type = (TypeDenoter) ast.V.visit(this, null);
    return ast.type;
  }

  // Declarations

  // Always returns null. Does not use the given object.
  public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    if (! ast.T.equals(eType))
      reporter.reportError ("body of function \"%\" has wrong type",
                            ast.I.spelling, ast.E.position);
    return null;
  }

  public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
    ast.D1.visit(this, null);
    ast.D2.visit(this, null);
    return null;
  }

  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitVarDeclaration(VarDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);

    return null;
  }

  // Array Aggregates

  // Returns the TypeDenoter for the Array Aggregate. Does not use the
  // given object.

  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    ast.elemCount = ast.AA.elemCount + 1;
    if (! eType.equals(elemType))
      reporter.reportError ("incompatible array-aggregate element", "", ast.E.position);
    return elemType;
  }

  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.E.visit(this, null);
    ast.elemCount = 1;
    return elemType;
  }

  // Record Aggregates

  // Returns the TypeDenoter for the Record Aggregate. Does not use the
  // given object.

  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    TypeDenoter fType = checkFieldIdentifier(rType, ast.I);
    if (fType != StdEnvironment.errorType)
      reporter.reportError ("duplicate field \"%\" in record",
                            ast.I.spelling, ast.I.position);
    ast.type = new MultipleFieldTypeDenoter(ast.I, eType, rType, ast.position);
    return ast.type;
  }

  public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    ast.type = new SingleFieldTypeDenoter(ast.I, eType, ast.position);
    return ast.type;
  }

  // Formal Parameters

  // Always returns null. Does not use the given object.

  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
    return null;
  }

  public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    ast.FPS.visit(this, null);
    return null;
  }

  public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    return null;
  }

  // Actual Parameters

  // Always returns null. Uses the given FormalParameter.

  public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);

    if (! (fp instanceof ConstFormalParameter))
      reporter.reportError ("const actual parameter not expected here", "",
                            ast.position);
    else if (! eType.equals(((ConstFormalParameter) fp).T))
      reporter.reportError ("wrong type for const actual parameter", "",
                            ast.E.position);
    return null;
  }

  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared (ast.I);
    else if (! (binding instanceof FuncDeclaration ||
                binding instanceof FuncFormalParameter))
      reporter.reportError ("\"%\" is not a function identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (binding instanceof ReFuncDeclaration ||
                binding instanceof FuncFormalParameter))
      reporter.reportError ("\"%\" is not a function identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof FuncFormalParameter))
      reporter.reportError ("func actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      TypeDenoter T = null;
      if (binding instanceof ReFuncDeclaration) {
        FPS = ((ReFuncDeclaration) binding).FPS;
        T = ((ReFuncDeclaration) binding).T;
      }
      if (binding instanceof FuncDeclaration) {
        FPS = ((FuncDeclaration) binding).FPS;
        T = ((FuncDeclaration) binding).T;
      } else {
        FPS = ((FuncFormalParameter) binding).FPS;
        T = ((FuncFormalParameter) binding).T;
      }
      if (! FPS.equals(((FuncFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for function \"%\"",
                              ast.I.spelling, ast.I.position);
      else if (! T.equals(((FuncFormalParameter) fp).T))
        reporter.reportError ("wrong type for function \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared (ast.I);
    else if (! (binding instanceof ProcDeclaration ||
                binding instanceof ProcFormalParameter))
      reporter.reportError ("\"%\" is not a procedure identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (binding instanceof ReProcDeclaration ||
                binding instanceof ProcFormalParameter))
      reporter.reportError ("\"%\" is not a procedure identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof ProcFormalParameter))
      reporter.reportError ("proc actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      if (binding instanceof ProcDeclaration)
        FPS = ((ProcDeclaration) binding).FPS;
      else
        FPS = ((ProcFormalParameter) binding).FPS;
      if (! FPS.equals(((ProcFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for procedure \"%\"",
                              ast.I.spelling, ast.I.position);
      if (binding instanceof ReProcDeclaration){
        FPS = ((ReProcDeclaration) binding).FPS;
      }
      else
        FPS = ((ProcFormalParameter) binding).FPS;
      if (! FPS.equals(((ProcFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for procedure \"%\"",
                              ast.I.spelling, ast.I.position);
      
    }
    return null;
  }

  public Object visitVarActualParameter(VarActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    if (! ast.V.variable)
      reporter.reportError ("actual parameter is not a variable", "",
                            ast.V.position);
    else if (! (fp instanceof VarFormalParameter))
      reporter.reportError ("var actual parameter not expected here", "",
                            ast.V.position);
    else if (! vType.equals(((VarFormalParameter) fp).T))
      reporter.reportError ("wrong type for var actual parameter", "",
                            ast.V.position);
    return null;
  }

  public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof EmptyFormalParameterSequence))
      reporter.reportError ("too few actual parameters", "", ast.position);
    return null;
  }

  public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof MultipleFormalParameterSequence))
      reporter.reportError ("too many actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((MultipleFormalParameterSequence) fps).FP);
      ast.APS.visit(this, ((MultipleFormalParameterSequence) fps).FPS);
    }
    return null;
  }

  public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof SingleFormalParameterSequence))
      reporter.reportError ("incorrect number of actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((SingleFormalParameterSequence) fps).FP);
    }
    return null;
  }

  // Type Denoters

  // Returns the expanded version of the TypeDenoter. Does not
  // use the given object.

  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
    return StdEnvironment.anyType;
  }

  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
    ast.T = (ArrayDeclarationOF) ast.T.visit(this, null);
    if ((Integer.valueOf(ast.IL.spelling).intValue()) == 0)
      reporter.reportError ("arrays must not be empty", "", ast.IL.position);
    return ast;
  }

  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
    return StdEnvironment.booleanType;
  }

  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
    return StdEnvironment.errorType;
  }

  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
      reportUndeclared (ast.I);
      return StdEnvironment.errorType;
    } else if (! (binding instanceof TypeDeclaration)) {
      reporter.reportError ("\"%\" is not a type identifier",
                            ast.I.spelling, ast.I.position);
      return StdEnvironment.errorType;
    }
    return ((TypeDeclaration) binding).T;
  }

  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
    ast.FT = (FieldTypeDenoter) ast.FT.visit(this, null);
    return ast;
  }

  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    ast.FT.visit(this, null);
    return ast;
  }

  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    return ast;
  }

  // Literals, Identifiers and Operators
  public Object visitCharacterLiteral(CharacterLiteral CL, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitIdentifier(Identifier I, Object o) {
    Declaration binding = idTable.retrieve(I.spelling);
    if (binding != null){
      I.decl = binding;
      
    }
    
    return binding;
  }

  public Object visitIntegerLiteral(IntegerLiteral IL, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitOperator(Operator O, Object o) {
    Declaration binding = idTable.retrieve(O.spelling);
    if (binding != null)
      O.decl = binding;
    return binding;
  }

  // Value-or-variable names

  // Determines the address of a named object (constant or variable).
  // This consists of a base object, to which 0 or more field-selection
  // or array-indexing operations may be applied (if it is a record or
  // array).  As much as possible of the address computation is done at
  // compile-time. Code is generated only when necessary to evaluate
  // index expressions at run-time.
  // currentLevel is the routine level where the v-name occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the object is addressed at run-time.
  // It returns the description of the base object.
  // offset is set to the total of any field offsets (plus any offsets
  // due to index expressions that happen to be literals).
  // indexed is set to true iff there are any index expressions (other
  // than literals). In that case code is generated to compute the
  // offset due to these indexing operations at run-time.

  // Returns the TypeDenoter of the Vname. Does not use the
  // given object.

  public Object visitDotVname(DotVname ast, Object o) {
    ast.type = null;
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    if (! (vType instanceof RecordTypeDenoter))
      reporter.reportError ("record expected here", "", ast.V.position);
    else {
      ast.type = checkFieldIdentifier(((RecordTypeDenoter) vType).FT, ast.I);
      if (ast.type == StdEnvironment.errorType)
        reporter.reportError ("no field \"%\" in this record type",
                              ast.I.spelling, ast.I.position);
    }
    return ast.type;
  }

  public Object visitSimpleVname(SimpleVname ast, Object o) {
    ast.variable = false;
    ast.type = StdEnvironment.errorType;
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared(ast.I);
    else
      if (binding instanceof ConstDeclaration) {
        ast.type = ((ConstDeclaration) binding).E.type;
        ast.variable = false;
      } else if (binding instanceof ConstFormalParameter) {
        ast.type = ((ConstFormalParameter) binding).T;
        ast.variable = false;
      } else if (binding instanceof VarFormalParameter) {
        ast.type = ((VarFormalParameter) binding).T;
        ast.variable = true;
      }else if(binding instanceof VarValueDeclaration){
            ast.type = ((VarValueDeclaration)binding).E.type;
            ast.variable = true;
        
      } else if (binding instanceof VarDeclaration){
          ast.type = ((VarDeclaration) binding).T;
          ast.variable = true;
        
      }else if (binding instanceof ForVarDecl) {
        ast.type = ((ForVarDecl) binding).E.type;
        ast.variable = false;
      }
      
      else
        reporter.reportError ("\"%\" is not a const or var identifier",
                              ast.I.spelling, ast.I.position);
    return ast.type;
  }

  public Object visitSubscriptVname(SubscriptVname ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (vType != StdEnvironment.errorType) {
      if (! (vType instanceof ArrayTypeDenoter))
        reporter.reportError ("array expected here", "", ast.V.position);
      else {
        if (! eType.equals(StdEnvironment.integerType))
          reporter.reportError ("Integer expression expected here", "",
				ast.E.position);
       //ast.type = ((ArrayTypeDenoter) vType).T.;
      }
    }
    return ast.type;
  }

  // Programs

  public Object visitProgram(Program ast, Object o) {
    ast.C.visit(this, null);
    return null;
  }

  // Checks whether the source program, represented by its AST, satisfies the
  // language's scope rules and type rules.
  // Also decorates the AST as follows:
  //  (a) Each applied occurrence of an identifier or operator is linked to
  //      the corresponding declaration of that identifier or operator.
  //  (b) Each expression and value-or-variable-name is decorated by its type.
  //  (c) Each type identifier is replaced by the type it denotes.
  // Types are represented by small ASTs.

  public void check(Program ast) {
    ast.visit(this, null);
  }

  /////////////////////////////////////////////////////////////////////////////

  public Checker (ErrorReporter reporter) {
    this.reporter = reporter;
    this.idTable = new IdentificationTable ();
    establishStdEnvironment();
  }

  private IdentificationTable idTable;
  private static SourcePosition dummyPos = new SourcePosition();
  private ErrorReporter reporter;

  // Reports that the identifier or operator used at a leaf of the AST
  // has not been declared.

  private void reportUndeclared (Terminal leaf) {
    reporter.reportError("\"%\" is not declared", leaf.spelling, leaf.position);
  }


  private static TypeDenoter checkFieldIdentifier(FieldTypeDenoter ast, Identifier I) {
    if (ast instanceof MultipleFieldTypeDenoter) {
      MultipleFieldTypeDenoter ft = (MultipleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      } else {
        return checkFieldIdentifier (ft.FT, I);
      }
    } else if (ast instanceof SingleFieldTypeDenoter) {
      SingleFieldTypeDenoter ft = (SingleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      }
    }
    return StdEnvironment.errorType;
  }


  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private TypeDeclaration declareStdType (String id, TypeDenoter typedenoter) {

    TypeDeclaration binding;

    binding = new TypeDeclaration(new Identifier(id, dummyPos), typedenoter, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ConstDeclaration declareStdConst (String id, TypeDenoter constType) {

    IntegerExpression constExpr;
    ConstDeclaration binding;

    // constExpr used only as a placeholder for constType
    constExpr = new IntegerExpression(null, dummyPos);
    constExpr.type = constType;
    binding = new ConstDeclaration(new Identifier(id, dummyPos), constExpr, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ProcDeclaration declareStdProc (String id, FormalParameterSequence fps) {

    ProcDeclaration binding;

    binding = new ProcDeclaration(new Identifier(id, dummyPos), fps,
                                  new EmptyCommand(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private FuncDeclaration declareStdFunc (String id, FormalParameterSequence fps,
                                          TypeDenoter resultType) {

    FuncDeclaration binding;

    binding = new FuncDeclaration(new Identifier(id, dummyPos), fps, resultType,
                                  new EmptyExpression(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // unary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private UnaryOperatorDeclaration declareStdUnaryOp
    (String op, TypeDenoter argType, TypeDenoter resultType) {

    UnaryOperatorDeclaration binding;

    binding = new UnaryOperatorDeclaration (new Operator(op, dummyPos),
                                            argType, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // binary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private BinaryOperatorDeclaration declareStdBinaryOp
    (String op, TypeDenoter arg1Type, TypeDenoter arg2type, TypeDenoter resultType) {

    BinaryOperatorDeclaration binding;

    binding = new BinaryOperatorDeclaration (new Operator(op, dummyPos),
                                             arg1Type, arg2type, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates small ASTs to represent the standard types.
  // Creates small ASTs to represent "declarations" of standard types,
  // constants, procedures, functions, and operators.
  // Enters these "declarations" in the identification table.

  private final static Identifier dummyI = new Identifier("", dummyPos);

  private void establishStdEnvironment () {

    // idTable.startIdentification();
    StdEnvironment.booleanType = new BoolTypeDenoter(dummyPos);
    StdEnvironment.integerType = new IntTypeDenoter(dummyPos);
    StdEnvironment.charType = new CharTypeDenoter(dummyPos);
    StdEnvironment.anyType = new AnyTypeDenoter(dummyPos);
    StdEnvironment.errorType = new ErrorTypeDenoter(dummyPos);

    StdEnvironment.booleanDecl = declareStdType("Boolean", StdEnvironment.booleanType);
    StdEnvironment.falseDecl = declareStdConst("false", StdEnvironment.booleanType);
    StdEnvironment.trueDecl = declareStdConst("true", StdEnvironment.booleanType);
    StdEnvironment.notDecl = declareStdUnaryOp("\\", StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.andDecl = declareStdBinaryOp("/\\", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.orDecl = declareStdBinaryOp("\\/", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);

    StdEnvironment.integerDecl = declareStdType("Integer", StdEnvironment.integerType);
    StdEnvironment.maxintDecl = declareStdConst("maxint", StdEnvironment.integerType);
    StdEnvironment.addDecl = declareStdBinaryOp("+", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.subtractDecl = declareStdBinaryOp("-", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.multiplyDecl = declareStdBinaryOp("*", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.divideDecl = declareStdBinaryOp("/", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.moduloDecl = declareStdBinaryOp("//", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.lessDecl = declareStdBinaryOp("<", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notgreaterDecl = declareStdBinaryOp("<=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.greaterDecl = declareStdBinaryOp(">", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notlessDecl = declareStdBinaryOp(">=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);

    StdEnvironment.charDecl = declareStdType("Char", StdEnvironment.charType);
    StdEnvironment.chrDecl = declareStdFunc("chr", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos), StdEnvironment.charType);
    StdEnvironment.ordDecl = declareStdFunc("ord", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos), StdEnvironment.integerType);
    StdEnvironment.eofDecl = declareStdFunc("eof", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.eolDecl = declareStdFunc("eol", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.getDecl = declareStdProc("get", new SingleFormalParameterSequence(
                                      new VarFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.putDecl = declareStdProc("put", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.getintDecl = declareStdProc("getint", new SingleFormalParameterSequence(
                                            new VarFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.putintDecl = declareStdProc("putint", new SingleFormalParameterSequence(
                                            new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.geteolDecl = declareStdProc("geteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.puteolDecl = declareStdProc("puteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.equalDecl = declareStdBinaryOp("=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
    StdEnvironment.unequalDecl = declareStdBinaryOp("\\=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);

  }

  
  // Cambios al analisador contextual Sebastian Campos
    @Override
    public Object visitElsifCommand(ElsifCommand ast, Object o) {
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
     ast.C.visit(this, null);
    return null; //To change body of generated methods, choose Tools | Templates.
    }
//Se visita el nodo para verificar el si el tipo es TypeDenoter
//Jose Rojas
    @Override
    public Object visitVarFormalDeclaration(VarFormalDeclaration ast, Object o) {
        ast.E.visit(this, null);       
        return null;
    }
    // Cambios al analisador contextual Sebastian Campos
//Se visita el nodo para verificar el si el tipo es TypeDenoter
//Jose Rojas
    @Override
    public Object visitVarValueDeclaration(VarValueDeclaration ast, Object o) {
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        idTable.enter(ast.I.spelling, ast);
        if(ast.duplicated)
            reporter.reportError("identifier \"%\" already declared", ast.I.spelling, ast.position);
        return null;
    }
//Se visita el nodo para verificar el si el tipo es TypeDenoter
//Jose Rojas
    @Override
    public Object visitArrayDeclarationOF(ArrayDeclarationOF ast, Object o) {
         ast.E.visit(this, null);
        return null;  
    }
//Se verifica que se indique el literal numerico 
//Jose Rojas
    @Override
    public Object visitArrayDeclarationDOBLEDOT(ArrayDeclarationDOBLEDOT ast, Object o) {
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.integerType))
            reporter.reportError("Integer expression expected here", "", ast.E.position);
       ast.C.visit(this, null);
       
        return null;
    }
// se agrega al analizador contexual para cheaquear que E2 sea un valor de tipo Integer ademas que se visita FOR VAR delcar para revisar su tipo que sea INT se protege y despues
    // se revisa contexrualmete el Until con la forma del repeat
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitForDoCommand(ForDoCommand ast, Object o) {
      
      TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
      if (! eType.equals(StdEnvironment.integerType))
        reporter.reportError("Integer expression expected here", "", ast.E.position);
      ast.VarDe.visit(this, null);
      ast.C1.visit(this,null);
      ast.C2.visit(this,null);
      idTable.closeScope();

      return null;
    }
// se agrega al analizador contexual para cheaquear que E2 sea un valor de tipo Integer ademas que se visita FOR VAR delcar para revisar su tipo que sea INT se protege y despues
    // se revisa contexrualmete el Until con la forma del repeat
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitForUntilCommand(ForUntilCommand ast, Object o) {
      
      TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
      if (! eType.equals(StdEnvironment.integerType))
        reporter.reportError("Integer expression expected here", "", ast.E.position);
      ast.VarDe.visit(this, null);
      ast.U.visit(this,null);
      idTable.closeScope();
      return null;
    }
// se agrega al analizador contexual para cheaquear que E2 sea un valor de tipo Integer ademas que se visita FOR VAR delcar para revisar su tipo que sea INT se protege y despues
    // se revisa contexrualmete el Until con la forma del repeat
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitForWhileCommand(ForWhileCommand ast, Object o) {
     //se revisa primero las expresiones sean Integer y se hace el scope
      TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
      if (! eType.equals(StdEnvironment.integerType))
        reporter.reportError("Integer expression expected here", "", ast.E.position);
      ast.VarDe.visit(this, null);
      ast.W.visit(this,null);
     idTable.closeScope();
      return null;
    }
    // se agrega al analizador contexual para cheaquear que sea un valor de tipo booleano
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
        ast.C1.visit(this, null);
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
        ast.C2.visit(this, null);
        return null; //To change body of generated methods, choose Tools | Templates.
    }
    // se agrega al analizador contexual para cheaquear que sea un valor de tipo booleano
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
        ast.C1.visit(this, null);
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
        ast.C2.visit(this, null);
        return null; //To change body of generated methods, choose Tools | Templates.
    }
    // se agrega al analizador contexual para cheaquear que sea un valor de tipo booleano
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) {
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
         ast.C1.visit(this, null);
         ast.C2.visit(this, null);
        return null;  //To change body of generated methods, choose Tools | Templates.
    }
    // se agrega al analizador contexual para cheaquear que sea un valor de tipo booleano
    //-sebastiaan Campos Zu???iga
    @Override
    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) {
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
         ast.C1.visit(this, null);
         ast.C2.visit(this, null);
        return null; //To change body of generated methods, choose Tools | Templates. //To change body of generated methods, choose Tools | Templates.
    }
     //-sebastiaan Campos Zu???iga
    // se revisa el arbol que protege el identificador del for
    @Override
    public Object visitForVarDecl(ForVarDecl ast, Object o) {
        
         TypeDenoter iType = (TypeDenoter) ast.E.visit(this, null);
         if (! iType.equals(StdEnvironment.integerType))
            reporter.reportError("Integer expression expected here", "", ast.E.position);
         else{
             idTable.openScope();
             idTable.enter(ast.I.spelling, ast);
             
             if (ast.duplicated)
            reporter.reportError ("identifier \"%\" already declared",ast.I.spelling, ast.position);
         }
       
    return null;
      
    }
       //hecho por Pablo Villafuerte
  static TypeDenoter typeSelector;
  public static boolean nuevoChooseCommand;
  public Object visitChooseCommand(ChooseCommand ast, Object o) {
        TypeDenoter eType;
        eType = (TypeDenoter) ast.E.visit(this, null);
        if(!eType.equals(StdEnvironment.integerType)&&!eType.equals(StdEnvironment.charType)){
            reporter.reportError("Integer or char expression expected here", "", ast.E.position);
        }
        typeSelector=eType;
        ast.Cas.visit(this,null);
        ast.C.visit(this, null);
        
        return null;
   }
   //hecho por Pablo Villafuerte
  boolean repetido=false;
  public Object visitWhenCase(WhenCase ast , Object o) {
        TypeDenoter Cl1Type;
        Cl1Type = (TypeDenoter) ast.CaseL.visit(this, null);
        TypeDenoter Cl2Type = (TypeDenoter) ast.CaseL2.visit(this, null);
        if(Cl2Type!=null){
            if(!Cl1Type.equals(typeSelector)||!Cl2Type.equals(typeSelector)){
                reporter.reportError("literals must be same type of expression expected here", "", ast.CaseL.position);
           }
        }
        else{
            if(!Cl1Type.equals(typeSelector)){
                reporter.reportError("literals must be same type of expression expected here", "", ast.CaseL.position);
           }
        }
        if(repetido==true){
            repetido=false;
            reporter.reportError("literals duplicated expected here", "", ast.CaseL2.position);
        }
        
         if(nuevoChooseCommand==true){
            AnterioresCharL.clear();
            AnterioresIntL.clear();
            nuevoChooseCommand=false;
        }
        ast.C.visit(this, null);
        
        
        
        return null;
    }

     //hecho por Pablo Villafuerte
     ArrayList<String> AnterioresIntL = new ArrayList<String>();
    @Override
    public Object visitIntegerCase(IntegerCase ast, Object o) {
        ast.type = StdEnvironment.integerType;
        String IntL=ast.IL.spelling;
        for (int i = 0; i < AnterioresIntL.size(); i++) {
            if(AnterioresIntL.get(i).equals(IntL)){
                repetido=true;
            }
        }
        
        AnterioresIntL.add(IntL);
        return ast.type;
    }
    
     ArrayList<String> AnterioresCharL = new ArrayList<String>();
    @Override
    public Object visitCharacterCase(CharacterCase ast, Object o) {
        ast.type = StdEnvironment.charType;
        
        String CharL=ast.CL.spelling;
        for (int i = 0; i < AnterioresCharL.size(); i++) {
            if(AnterioresCharL.get(i).equals(CharL)){
                repetido=true;
            }
        }
        
        AnterioresCharL.add(CharL);
        
        return ast.type;
    }
    
     //hecho por Pablo Villafuerte
    @Override
    public Object visitEmptyCase(EmptyCase aThis, Object o) {
        return null;
    }
    //hecho por Pablo Villafuerte
    @Override
    public Object visitPrivateDeclaration(PrivateDeclaration ast, Object o) {
        idTable.openScope();
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        idTable.closeScope();
      return null;

    }

     //hecho por Pablo Villafuerte
    @Override
    public Object visitSequentialCase(SequentialCase ast, Object o) {
        ast.Cas1.visit(this, null);
        ast.Cas2.visit(this, null);
        
        return null;
    }

    @Override
    public Object visitRecursiveProcFunc(RecursiveProcFunc ast, Object o) {
        ast.RecPrFun.visit(this, null);
        globalInt =1;
        ast.RecPrFun.visit(this, null);
        while(idTable.getLevel()!=1){
             idTable.closeScope();
        }
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitReProcDeclaration(ReProcDeclaration ast, Object o) {
        
        
        if(globalInt==0){
            idTable.enter (ast.I.spelling, ast); // permits recursion
             if (ast.duplicated){
                reporter.reportError ("identifier \"%\" already declared",ast.I.spelling, ast.position);
             }
             ast.FPS.visit(this, null);
             idTable.openScope();
             
             
        }
        if(globalInt==1){
            idTable.openScope();
            ast.C.visit(this, null);
            idTable.closeScope();
        }
        
        
 
    return null;
    }

    @Override
    public Object visitReFuncDeclaration(ReFuncDeclaration ast, Object o) {
        
        if(globalInt==0){
            ast.T = (TypeDenoter) ast.T.visit(this, null);
            idTable.enter (ast.I.spelling, ast); // permits recursion
            if (ast.duplicated)
                reporter.reportError ("identifier \"%\" already declared",
                ast.I.spelling, ast.position);
        }
                ast.FPS.visit(this, null);
               idTable.openScope();
                
        
        if(globalInt==1){
             idTable.openScope();
            TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
            if (! ast.T.equals(eType))
                reporter.reportError ("body of function \"%\" has wrong type",ast.I.spelling, ast.E.position);
             idTable.closeScope();
        }
            
        
        
    return null;
  } //To change body of generated methods, choose Tools | Templates.

    @Override
    public Object visitForWhileExtraCommand(ForWhileExtraCommand ast, Object o) {
          TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
         ast.C1.visit(this, null);
         ast.C2.visit(this, null);
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitForUntilExtraCommand(ForUntilExtraCommand ast, Object o) {
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
         ast.C1.visit(this, null);
         ast.C2.visit(this, null);
        return null; //To change body of generated methods, choose Tools | Templates.
    }
    

  


 
}
