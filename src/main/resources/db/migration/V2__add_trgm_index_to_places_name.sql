-- 例：名前の曖昧検索用インデックス（将来使う予定）
create index if not exists places_name_trgm_idx
  on public.places
  using gin (name gin_trgm_ops);
