package org.example.lab4backend.dto.response;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse<T> {
    private List<T> data;
    private PaginationMeta pagination;

    public PageableResponse(List<T> content, int page, int size, long total) {
        this.data = content;
        this.pagination = new PaginationMeta(page, size, total);
    }

    @Data
    public static class PaginationMeta {
        private int page;
        private int pageSize;
        private long total;

        public PaginationMeta(int page, int pageSize, long total) {
            this.page = page;
            this.pageSize = pageSize;
            this.total = total;
        }
    }
}
