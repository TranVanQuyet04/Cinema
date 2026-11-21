export const isMovieShowingOnDate = (movie, date) => Boolean(
  movie?.release && date && movie.release <= date && (!movie.end || date <= movie.end)
);
