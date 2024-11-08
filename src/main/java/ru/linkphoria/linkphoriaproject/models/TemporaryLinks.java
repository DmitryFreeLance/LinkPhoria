package ru.linkphoria.linkphoriaproject.models;

import lombok.Data;

@Data
public class TemporaryLinks {
    private String startTime;
    private String endTime;
    private boolean isActive;
    private String tLink;
}
