CREATE TABLE public."color_log"
(
    id SERIAL PRIMARY KEY NOT NULL,
    color TEXT NOT NULL,
    date TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX "color-log_id_uindex" ON public."color-log" (id);

CREATE SEQUENCE hibernate_sequence
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;