float levels(vec3 position, float inner_width, float outter_width, float freq, vec3 used_axes) {
    vec3 tmp_position = pos * freq;
    vec3 distance_to_level = abs(tmp_position - round(tmp_position)) / outter_width / freq;

    float min_distance = 1;
    for (int i = 0; i < 3; i++) {
        if (used_axes[i] == 1 && distance_to_level[i] < min_distance) {
            min_distance = distance_to_level[i];
        }
    }

    return clamp(min_distance, 0, 1);
}