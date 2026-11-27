import test from 'node:test';
import assert from 'node:assert/strict';
import { normalizeBookingExpiry } from './bookingExpiry.js';

test('uses server epoch for hold expiry even when timestamp has no timezone', () => {
  const result = normalizeBookingExpiry({ holdExpiresAt: '2026-11-27T04:27:14', holdExpiresAtEpochMs: 1795753634000 });
  assert.equal(new Date(result.holdExpiresAt).getTime(), 1795753634000);
});

test('preserves old responses and null expiry', () => {
  assert.equal(normalizeBookingExpiry({ holdExpiresAt: 'legacy' }).holdExpiresAt, 'legacy');
  assert.equal(normalizeBookingExpiry({ holdExpiresAt: null, holdExpiresAtEpochMs: null }).holdExpiresAt, null);
});
