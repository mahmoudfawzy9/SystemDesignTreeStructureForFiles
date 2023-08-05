GRANT CONNECT ON DATABASE stc TO docker;

--select u.id from stc.public.users u where id =19;

INSERT INTO public.user_tokens(id, token, update_time, user_id) VALUES (1, 'abcdefg', now(), 1);
