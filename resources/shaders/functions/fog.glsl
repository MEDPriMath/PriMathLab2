vec4 mix(vec4 color1, vec4 color2, float k) {
    return color1 * (1 - k) + color2 * k;
}
vec4 fog(in vec4 color, in vec3 pos, in vec3 view_pos) {
    float distance = length(pos - view_pos);
    float fog_k = clamp(distance / 56, 0, 1);
    fog_k *= fog_k;
    return mix(color,  vec4(0.2, 0.5, 1, 1), fog_k);
}