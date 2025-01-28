package org.zan.demo.data.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.results.graph.collection.internal.BagInitializer;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class UpdateRequestDto {
    private UUID id;
    private BigDecimal quantity;
}
