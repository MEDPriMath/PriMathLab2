vec3 proj_to_pos(mat4 proj) {
    float ratio = sqrt(4 / (proj[0][0] * proj[0][0] + proj[2][0] * proj[2][0]));
    float cosb = proj[0][0] / 2 * ratio;
    float sinb = proj[2][0] / 2 * ratio;
    float cosa = proj[1][1] / 2;
    float sina = - proj[1][2];

    float l1 = proj[3][0] / 2 * ratio;
    float l2 = proj[3][1] / 2;
    float l3 = proj[3][2] + 0.004;

    vec3 pos = vec3(0);

    if (sina == 0 && sinb == 0) {
        pos.x = l1 / cosb;
        pos.y = l2 / cosa;
        pos.z = - l3 / cosa / cosb;
    } else if (sina == 0) {
        pos.y = l2 / cosa;
        l3 /= cosa;
        pos.x = l3 * sinb + l1 * cosb;
        pos.z = (l1 - pos.x * cosb) / sinb;
    } else if (sinb == 0) {
        pos.x = l1 / cosb;
        pos.z = (l3 * cosa - l2 * sina) / (sina * sina * cosb - cosa * cosa * cosb);
        pos.y = (l2 + pos.z * sina * cosb) / cosa;
    } else {
        float k1 = sina * sinb + sinb * cosa * cosa / sina;
        float k2 = sina * cosb + cosa * cosa * cosb / sina;

        pos.x = (l2 + l3 * cosa / sina + l1 * k2 / sinb) / (k1 + cosb * k2 / sinb);
        pos.z = l1 / sinb - pos.x * cosb / sinb;
        pos.y = (pos.x * sinb * cosa - pos.z * cosa * cosb - l3) / sina;
    }
    return -pos;
}