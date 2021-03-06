/*
 * Sonar ESQL Plugin
 * Copyright (C) 2013 Thomas Pohl and EXXETA AG
 * http://www.exxeta.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package com.exxeta.iss.sonar.esql.check;

import java.util.List;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;

public class CheckUtils {
	private CheckUtils() {
	}



	public static boolean equalNodes(AstNode node1, AstNode node2) {
		if (!node1.getType().equals(node2.getType())
				|| node1.getNumberOfChildren() != node2.getNumberOfChildren()) {
			return false;
		}
		
		List<AstNode> children1 = node1.getChildren();
		List<AstNode> children2 = node2.getChildren();
		for (int i = 0; i < children1.size(); i++) {
			if (!equalNodes(children1.get(i), children2.get(i))) {
				return false;
			}
		}
		return true;
	}


	public static boolean containsValue(List<Token> list, String value) {
		for (Token currentToken : list) {
			if (currentToken.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}
}