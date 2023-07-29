create table shortener_url(
	id serial primary key,
	origin_url text not null,
	short_url text not null,
	hash_url text not null,
	time_register timestamp not null,
	last_access timestamp,
	total_clicks integer
);