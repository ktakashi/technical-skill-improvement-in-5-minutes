package io.ktakashi.tsii5m.parser.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CSVParser {
    public List<List<String>> parse(InputStream is) throws IOException {
        return parse(new InputStreamReader(is));
    }

    public List<List<String>> parse(Reader reader) throws IOException {
        try (var pushbackReader = new PushbackReader(reader)) {
            var records = new ArrayList<List<String>>();
            while (true) {
                var record = readRecord(pushbackReader);
                if (record.isEmpty()) {
                    return records;
                }
                records.add(record.get());
            }
        }
    }
    private enum TokenType {
        CRLF,
        FIELD
    }
    private static class Token {
        private final TokenType type;
        private final String value;

        private Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
        private Token(TokenType type) {
            this.type = type;
            this.value = null;
        }
    }

    private Optional<List<String>> readRecord(PushbackReader reader) throws IOException {
        var record = new ArrayList<String>();
        while (true) {
            var token = readField(reader);
            if (token.type == TokenType.CRLF) {
                if (record.size() == 0) {
                    return Optional.empty();
                }
                return Optional.of(record);
            }
            record.add(token.value);
        }
    }

    private Token readField(PushbackReader reader) throws IOException {
        int c = reader.read();
        if (c == '"') {
            return new Token(TokenType.FIELD, readEscaped(reader));
        } else if (c == '\n' || c == -1) {
            return new Token(TokenType.CRLF);
        } else if (c == '\r') {
           int c2 = reader.read();
           if (c2 == '\n') {
               return new Token(TokenType.CRLF);
           }
           throw new IOException("Invalid character CR");
        }
        reader.unread(c);
        return new Token(TokenType.FIELD, readUnescaped(reader));
    }

    private String readUnescaped(PushbackReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c == ',' || c == -1) {
                return sb.toString();
            }
            if (c == '"') {
                throw new IOException("Double quote is not allowed as a field");
            }
            if (c == '\r' || c == '\n') {
                reader.unread(c);
                return sb.toString();
            }
            if (0x20 <= c && c <= 0x7E) {
                sb.append((char)c);
                continue;
            }
            throw new IOException(String.format("Textdata must be range of 0x20-0x7E, 0x%02x", c));
        }
    }

    private String readEscaped(PushbackReader reader) throws IOException{
        StringBuilder sb = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c == '"') {
                int c2 = reader.read();
                if (c2 == '"') {
                    sb.append('"');
                    continue;
                }
                if (c2 == ',' || c2 == -1) {
                    return sb.toString();
                }
                if (c2 == '\r' || c2 == '\n') {
                    reader.unread(c2);
                    return sb.toString();
                }
                throw new IOException("Invalid format");
            }
            sb.append((char)c);
        }
    }
}
