IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'security_db')
    BEGIN
        CREATE DATABASE security_db;
    END
GO