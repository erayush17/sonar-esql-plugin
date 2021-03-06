package com.exxeta.iss.sonar.esql.lexer;

import org.sonar.sslr.channel.Channel;
import org.sonar.sslr.channel.CodeReader;

import com.exxeta.iss.sonar.esql.api.EsqlTokenType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;

public class NewLineChannel extends Channel<Lexer> {

	private final LexerState lexerState;

	public NewLineChannel(LexerState lexerState) {
		this.lexerState = lexerState;
	}

	@Override
	public boolean consume(CodeReader code, Lexer output) {
		char ch = (char) code.peek();
		checkForBrackets(ch);
		if ((ch == '\\') && isNewLine(code.charAt(1))) {
			// Explicit line joining
			code.pop();
			joinLines(code);
			return true;
		}
		if (isNewLine(ch)) {
			if (processNewLine(code, output)) {
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean processNewLine(CodeReader code, Lexer output) {
		if (isImplicitLineJoining()) {
			// Implicit line joining
			joinLines(code);
			return true;
		}
		if (output.getTokens().isEmpty()
				|| (output.getTokens().get(output.getTokens().size() - 1)
						.getType().equals(EsqlTokenType.NEWLINE))) {
			// Blank line
			consumeEOL(code);
			return true;
		}
		// NEWLINE token
		output.addToken(Token.builder().setLine(code.getLinePosition())
				.setColumn(code.getColumnPosition()).setURI(output.getURI())
				.setType(EsqlTokenType.NEWLINE)
				.setValueAndOriginalValue("\n").setGeneratedCode(true).build());
		consumeEOL(code);
		return false;
	}

	private void checkForBrackets(char ch) {
		switch (ch) {
		case '[':
		case '(':
		case '{':
			lexerState.brackets++;
			break;
		case ']':
		case ')':
		case '}':
			lexerState.brackets--;
			break;
		default:
			break;
		}
	}

	private void joinLines(CodeReader code) {
		while (Character.isWhitespace(code.peek())) {
			code.pop();
		}
		lexerState.joined = true;
	}

	private static void consumeEOL(CodeReader code) {
		if ((code.charAt(0) == '\r') && (code.charAt(1) == '\n')) {
			// \r\n
			code.pop();
			code.pop();
		} else {
			// \r or \n
			code.pop();
		}
	}

	private static boolean isNewLine(char ch) {
		return (ch == '\n') || (ch == '\r');
	}

	private boolean isImplicitLineJoining() {
		return lexerState.brackets > 0;
	}

}
