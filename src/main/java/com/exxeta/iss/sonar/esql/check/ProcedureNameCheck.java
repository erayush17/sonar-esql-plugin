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
 */
package com.exxeta.iss.sonar.esql.check;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.exxeta.iss.sonar.esql.api.EsqlGrammar;
import com.sonar.sslr.api.AstNodeType;

@Rule(key = ProcedureNameCheck.CHECK_KEY, priority = Priority.MAJOR, name = "Procedure names should comply with a naming convention", tags = Tags.CONVENTION)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("10min")
@ActivatedByDefault
@BelongsToProfile(title=CheckList.SONAR_WAY_PROFILE,priority=Priority.MAJOR)
public class ProcedureNameCheck extends AbstractNameCheck {
	public static final String CHECK_KEY = "ProcedureName";
	
	private static final String DEFAULT_FORMAT = "^[a-z][a-zA-Z0-9]{1,30}$";
	
	@RuleProperty(key = "format", 
			description="regular expression",
			defaultValue = "" + DEFAULT_FORMAT)
	public String format = DEFAULT_FORMAT;

	public String getFormat() {
		return format;
	}

	@Override
	public String typeName() {
		return "procedure";
	}

	@Override
	public AstNodeType getType() {
		return EsqlGrammar.PROCEDURE_DECLARATION;
	}
	

}
