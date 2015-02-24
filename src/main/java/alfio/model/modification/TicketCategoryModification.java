/**
 * This file is part of alf.io.
 *
 * alf.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alf.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alf.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package alfio.model.modification;

import alfio.model.TicketCategory;
import alfio.util.MonetaryUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;

@Getter
public class TicketCategoryModification {

    private final Integer id;
    private final String name;
    private final int maxTickets;
    private final DateTimeModification inception;
    private final DateTimeModification expiration;
    private final String description;
    private final BigDecimal price;
    private final boolean tokenGenerationRequested;
    private final String dateString;

    @JsonCreator
    public TicketCategoryModification(@JsonProperty("id") Integer id,
                                      @JsonProperty("name") String name,
                                      @JsonProperty("maxTickets") int maxTickets,
                                      @JsonProperty("inception") DateTimeModification inception,
                                      @JsonProperty("expiration") DateTimeModification expiration,
                                      @JsonProperty("description") String description,
                                      @JsonProperty("price") BigDecimal price,
                                      @JsonProperty("tokenGenerationRequested") boolean tokenGenerationRequested,
                                      @JsonProperty("dateString") String dateString) {
        this.id = id;
        this.name = name;
        this.maxTickets = maxTickets;
        this.inception = inception;
        this.expiration = expiration;
        this.description = description;
        this.price = price;
        this.tokenGenerationRequested = tokenGenerationRequested;
        this.dateString = dateString;
    }

    public int getPriceInCents() {
        return Optional.ofNullable(price).map(MonetaryUtil::unitToCents).orElse(0);
    }

    public static TicketCategoryModification fromTicketCategory(TicketCategory tc, ZoneId zoneId) {
        return new TicketCategoryModification(tc.getId(),
                tc.getName(),
                tc.getMaxTickets(),
                DateTimeModification.fromZonedDateTime(tc.getInception(zoneId)),
                DateTimeModification.fromZonedDateTime(tc.getExpiration(zoneId)),
                tc.getDescription(),
                tc.getPrice(),
                tc.isAccessRestricted(), "");
    }
}
