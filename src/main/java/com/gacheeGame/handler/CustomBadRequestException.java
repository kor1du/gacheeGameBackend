package com.gacheeGame.handler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomBadRequestException extends RuntimeException
{
    public CustomBadRequestException(String message)
    {
        super(message);
    }
}