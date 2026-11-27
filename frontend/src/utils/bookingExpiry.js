export function normalizeBookingExpiry(booking) {
  if (!booking || typeof booking !== 'object') return booking;
  const epoch = booking.holdExpiresAtEpochMs;
  if (!Number.isFinite(epoch)) return booking;
  const expiry = new Date(epoch);
  return Number.isNaN(expiry.getTime()) ? booking : { ...booking, holdExpiresAt: expiry.toISOString() };
}
