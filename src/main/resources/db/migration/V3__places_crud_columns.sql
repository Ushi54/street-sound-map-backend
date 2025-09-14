-- 更新日時を追加
alter table public.places
  add column if not exists updated_at timestamptz default now();

-- updated_at を更新時に自動更新するトリガ（冪等）
create or replace function set_updated_at()
returns trigger as $$
begin
  new.updated_at = now();
  return new;
end;
$$ language plpgsql;

drop trigger if exists trg_places_updated_at on public.places;
create trigger trg_places_updated_at
before update on public.places
for each row execute function set_updated_at();

-- テキスト検索最適化（pg_trgm）
create index if not exists places_name_trgm_idx on public.places using gin (name gin_trgm_ops);
create index if not exists places_addr_trgm_idx on public.places using gin (address gin_trgm_ops);
create index if not exists places_desc_trgm_idx on public.places using gin (description gin_trgm_ops);

-- 緯度経度をAPIで取りやすいビュー（読み取り専用）
create or replace view public.v_places as
select
  p.id, p.name, p.description, p.address,
  p.created_by, p.created_at, p.updated_at,
  st_x(st_astext(p.geom::geometry)) as lon,
  st_y(st_astext(p.geom::geometry)) as lat
from public.places p;
