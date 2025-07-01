package org.example.Server;

import java.io.*;

public interface IServerStrategy {
    void serverstrategy(InputStream input , OutputStream output) throws IOException;
}
