/*
 * Copyright 2024 Wafer EX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nokia.mid.messaging;

import javax.microedition.io.Connection;
import java.io.IOException;
import java.io.InterruptedIOException;

public interface MessageConnection extends Connection {
    public static final int TEXT_MESSAGE = 1;
    public static final int BINARY_MESSAGE = 2;

    public Message newMessage(int type);

    public Message newMessage(int type, String address);

    public void send(Message msg) throws IOException, InterruptedIOException;

    public Message receive() throws IOException, InterruptedIOException;

    public void registerMessageListener(MessageListener l) throws java.io.IOException;

    public int numberOfMessages(Message msg);
}