/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package kafka.api

import java.nio.ByteBuffer
import org.apache.kafka.common.requests.AbstractRequestResponse

private[kafka] abstract class GenericResponseAndHeader(val correlationId: Int,
                                                       val body: AbstractRequestResponse,
                                                       val name: String,
                                                       override val requestId: Option[Short] = None)
  extends RequestOrResponse(requestId) {

  def writeTo(buffer: ByteBuffer) {
    buffer.putInt(correlationId)
    body.writeTo(buffer)
  }

  def sizeInBytes(): Int = {
    4 /* correlation id */ +
    body.sizeOf()
  }

  override def toString(): String = {
    describe(true)
  }

  override def describe(details: Boolean): String = {
    val strBuffer = new StringBuilder
    strBuffer.append("Name: " + name)
    strBuffer.append("; CorrelationId: " + correlationId)
    strBuffer.append("; Body: " + body.toString)
    strBuffer.toString()
  }
}
