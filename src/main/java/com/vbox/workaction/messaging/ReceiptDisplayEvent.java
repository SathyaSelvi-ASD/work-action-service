package com.vbox.workaction.messaging;

import java.time.Instant;

/** Must remain JSON-compatible with Disclosure's receipt-display event. */
public record ReceiptDisplayEvent(
        String eventId,
        String receiptId,
        String workActionId,
        String customerId,
        String referenceNumber,
        String displayedBy,
        Instant displayedAt) {
}
