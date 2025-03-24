CREATE TABLE post_images (
    id BIG SERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE CASCADE
)