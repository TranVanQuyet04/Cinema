import test from 'node:test';
import assert from 'node:assert/strict';
import { isMovieShowingOnDate } from './movieShowingWindow.js';

test('includes the release and end dates, even with a stale upcoming status', () => {
  const movie = { release: '2026-11-11', end: '2026-11-24', status: 'UPCOMING' };
  assert.equal(isMovieShowingOnDate(movie, '2026-11-11'), true);
  assert.equal(isMovieShowingOnDate(movie, '2026-11-24'), true);
  assert.equal(isMovieShowingOnDate(movie, '2026-11-10'), false);
  assert.equal(isMovieShowingOnDate(movie, '2026-11-25'), false);
});

test('requires a release date and allows an unspecified end date', () => {
  assert.equal(isMovieShowingOnDate({ release: '', end: '' }, '2026-11-21'), false);
  assert.equal(isMovieShowingOnDate({ release: '2026-11-11', end: '' }, '2026-11-21'), true);
});
