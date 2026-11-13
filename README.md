# JAVA Mini Compiler
A mini programming language compiler built from scratch in Java.
Featuring a handwritten lexer, parser, bytecode generator, and virtual machine.

## Features
- Integer variables and assignments
- Arithmetic operations (`+`, `-`, `*`, `/`)
- Parenthesized expressions
- `print` statements
- `if` conditional statements
- `while` loops
- Stack-based bytecode execution

## Architecture
**Pipeline:** `Source Code → Lexer → Parser → AST → Bytecode Generator → Virtual Machine → Output`
- **Source Code:** MiniLang written by user, stored in `program.mini`
- **Lexer:** Reads source code and divide it into tokens
- **Parser:** Reads tokens, build Abstract Syntax Tree
- **AST (Abstract Syntax Tree):** Represents the meaning and the order of operations
- **Bytecode Generator:** Generates instructions from AST so that VM can execute
- **Virtual Machine:** Executes instructions by stack, variables and program counter(pc)
- **Output:** Prints results to the console

